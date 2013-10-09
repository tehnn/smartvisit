package software.utehn.dev;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class TargetGroupActivity extends Activity {
	
	TextView tvTargetGroupName;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.target_group_layout);
		tvTargetGroupName=(TextView)findViewById(R.id.tvTargetName);
		tvTargetGroupName.setText("รายชื่อ: "+GlobalVar.TargetGroup);
		
		
	}

}
