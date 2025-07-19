import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// ------------------------------------------  DATE AND TIME
public class DateTime
{
    LocalDateTime datetime;
    String date;
    String time;

    public DateTime()
    {
        datetime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm:ss");
        date = datetime.format(formatter);
        time = datetime.format(formatterTime);
    }

    public String getDate()
    {
        return date;
    }

    public String getTime()
    {
        return time;
    }
}

