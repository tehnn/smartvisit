package software.utehn.dev;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class PersonDetailActivity extends Activity {
	/** Called when the activity is first created. */

	ListView lvPersonMenu;
	TabHost mTabHost;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person_detail_layout);
		init_lvPersonMenu();

	}// end onCreate

	void init_lvPersonMenu() {
		lvPersonMenu = (ListView) findViewById(R.id.lvPersonMenu);
		String[] menu_itm = getResources().getStringArray(
				R.array.lvPersonMenuItm);

		lvPersonMenu.setAdapter(new ArrayAdapter<String>(this,
				R.layout.lv_peron_menu_row, R.id.tv_menu_name, menu_itm));

		lvPersonMenu.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(getApplicationContext(),
						"Click ListItem Number " + position, Toast.LENGTH_LONG)
						.show();

				// dialog

				

				//

			}
		});
	}

	public void person_imv_clicked(View v) {
		Toast.makeText(getApplicationContext(), "Click ", Toast.LENGTH_LONG)
				.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.person_detail_activity_menu, menu);
		return true;
	}

}// end Main Class
