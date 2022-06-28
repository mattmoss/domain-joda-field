package demo

import grails.testing.gorm.DataTest
import grails.testing.services.ServiceUnitTest
import org.joda.time.DateTime
import spock.lang.Specification

class AppointmentServiceSpec extends Specification
        implements ServiceUnitTest<AppointmentService>, DataTest {

    def setup() {
        mockDomain Appointment
    }

    void "dateTime and dirty flag"() {
        given:
        DateTime now = DateTime.now()

        when:
        Appointment appt = service.create(now)

        then:
        !appt.dirty

        when:
        service.reschedule(appt, '2022-10-01T09:30:00.000-05:00')

        then:
        appt.dirty
        appt.isDirty('dateTime')

        when:
        service.update(appt)

        then:
        !appt.dirty
    }
}
