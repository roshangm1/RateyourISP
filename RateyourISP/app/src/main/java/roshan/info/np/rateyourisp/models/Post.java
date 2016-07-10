package roshan.info.np.rateyourisp.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;

@IgnoreExtraProperties
public class Post {
    public String reviewer,review,imageUrl;
    public float aveRate,r1,r2,r3,r4,ovR1,ovR2,ovR3,ovR4;

    public int rateCount;

    public Post() {}

    public Post(String reviewer,float ovR1, float ovR2, float ovR3, float ovR4,float r1, float r2, float r3, float r4, int rateCount, String review, float aveRate, String imageUrl) {
        this.reviewer=reviewer;
        this.review=review;
        this.aveRate=aveRate;
        this.r1=r1;this.r2=r2;this.r3=r3;this.r4=r4;
        this.ovR1=ovR1;this.ovR2=ovR2;this.ovR3=ovR3;this.ovR4=ovR4;
        this.rateCount = rateCount;
        this.imageUrl=imageUrl;

    }

    @Exclude
    public HashMap<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("reviewer",reviewer);
        result.put("review",review);
        result.put("r1",r1);
        result.put("r2",r2);
        result.put("r3",r3);
        result.put("r4",r4);
        result.put("imageUrl",imageUrl);


        return result;
    }

    @Exclude
    public HashMap<String, Object> toMapRate() {
        HashMap<String, Object> result = new HashMap<>();
        //result.put("uid",uid);
        result.put("rate1",ovR1);
        result.put("rate2",ovR2);
        result.put("rate3",ovR3);
        result.put("rate4",ovR4);
        result.put("rateCount",rateCount);
        result.put("aveRate",aveRate);

        //result.put("time_stamp",time_stamp);

        return result;
    }

}
