package demo

import org.jadira.usertype.dateandtime.joda.PersistentDateTime
import org.joda.time.DateTime

class Appointment {

    DateTime dateTime

    // This is the property setter,
    void setDateTime(DateTime dt) {
        dateTime = dt
        print "Appointment time updated to $dateTime"
    }

    void change(String str) {
        DateTime dt = DateTime.parse(str)

        // NOTE: This method bypasses the property setter, which is where the dirty tracking is added.
        // Change this to `setDateTime(dt)` to use the instance marked dirty.
        dateTime = dt
    }

    static constraints = {
    }

    static mapping = {
        dateTime type: PersistentDateTime
    }
}
