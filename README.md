# Notes on Joda DateTime field in a domain class

As provided, running the single unit test will fail.
After calling `AppointmentService.reschedule` but before saving the instance, the instance should be marked dirty, but it isn't.

The implementation of `reschedule` calls `Appointment.change` which is implemented basically as:
```groovy
    DateTime dt = DateTime.parse(str)
    dateTime = dt
```
This creates a new `DateTime` instance and sets the **field**. However, this does not set the dirty flag and the test fails.

A workaround is in `AppointmentService.reschedule`: uncomment line 17
```groovy
    appt.dateTime = null    // Uncomment this line
    appt.change(requestedDateTime)
```
and the test will pass.
By setting the `dateTime` **property**, the instance will be marked dirty.
Note the choice of word: **property**.
That workaround calls a property setter.
Whether it's one you've written or a generated setter, this workaround sets the *property*.

Setting the **field** does not mark the instance dirty, but setting the **property** does.
This is because Grails/GORM implements dirty-checking by wrapping the setter (whether it is generated or manually written).

So the workaround works because one line is setting the dirty flag (by way of the property setter), and the following line is setting the desired value.

Better than the workaround would be to change the implementation of `Appointment.change`.
```groovy
    DateTime dt = DateTime.parse(str)
    setDateTime(dt)
```
This will make it clear that you're using the **property** setter and gaining the dirty checking behavior.
Another alternative is to explicitly call `markDirty` at the same time that you change the field.
A final alternative (and perhaps the best) is to review, refactor, and revise how the property setter is written and funnel all property mutations through that.
