package pojos;

public class BookingResponsePojo {

    private Integer bookingid;
    private BookingPojo booking;


    //only getters bcs we won't assign new data
    public Integer getBookingid() {
        return bookingid;
    }

    public BookingPojo getBooking() {
        return booking;
    }

    @Override
    public String toString() {
        return "BookingResponsePojo{" +
                "bookingid=" + bookingid +
                ", booking=" + booking +
                '}';
    }
}
