package demo

import grails.gorm.transactions.Transactional
import groovy.transform.CompileStatic
import org.joda.time.DateTime

@CompileStatic
@Transactional
class AppointmentService {

    def create(DateTime when) {
        new Appointment(dateTime: when).save()
    }

    def reschedule(Appointment appt, String requestedDateTime) {
        // NOTE: Uncomment this line as a workaround.
        // appt.dateTime = null
        appt.change(requestedDateTime)
    }

    def update(Appointment appt) {
        appt.save()
    }
}
