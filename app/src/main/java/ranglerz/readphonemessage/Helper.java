package ranglerz.readphonemessage;

/**
 * Created by User-10 on 29-Aug-17.
 */

public class Helper {

    int id;
    String lat, lng, time, date;

    //setter methods
    public void setId(int id){
        this.id = id;
    }

    public void setLat(String lat){
        this.lat = lat;
    }
    public void setLng(String lng){
        this.lng = lng;
    }
    public void setdate(String date){
        this.date = date;
    }
    public void setTime(String time){
        this.time = time;
    }

    //gerter methods

    public int getId(){
        return this.id;
    }

    public String getLat(){
        return this.lat;
    }

    public String getLng(){
        return this.lng;
    }
    public String getDate(){
        return this.date;
    }
    public String getTime(){
        return this.time;
    }
}
