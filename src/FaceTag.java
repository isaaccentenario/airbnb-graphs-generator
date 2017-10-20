import com.restfb.Facebook;

public class FaceTag {
	   @Facebook("tag_uid")
	   private String tagUid;

	   @Facebook("tag_text")
	   private String tagText;
	   
	   public FaceTag(String id, String text){
		   this.tagUid = id;
		   this.tagText = text;
	   }
}