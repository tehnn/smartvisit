package software.utehn.dev;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class HouseActivity extends Activity {

	ListView lvHouse;
	EditText txtFindHouse;
	ArrayAdapter<String> adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.house_list_layout);

		init_lvHouse();

		txtFindHouse = (EditText) findViewById(R.id.txtFindTarget);
		// / เหตุการ TextChange
		txtFindHouse.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2,
					int arg3) {
				// HouseActivity.this.adapter.getFilter().filter(cs);
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {

			}
		});

	}// end onCreate

	void init_lvHouse() {

		lvHouse = (ListView) findViewById(R.id.lvTarget);

		String all_list_itms[] = { "อุเทน", "นาเดีย", "มอส", "ปฏิพาน",
				"สุธิราช", "เกษม", "ใบเฟิร์น", "รสนา", "สุเทพ", "มาร์ค", "แม้ว" };

		adapter = new ArrayAdapter<String>(this, R.layout.lv_house_row,
				R.id.tvHouseOwner, all_list_itms);
		lvHouse.setAdapter(adapter);
		lvHouse.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Log.d("TEST", String.valueOf(arg3));
			}
		});

	}

	public void btnFindHouse_Clicked(View v) {

		adapter.getFilter().filter(txtFindHouse.getText().toString());

		InputMethodManager inputMethodManager = (InputMethodManager) this
				.getSystemService(Activity.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus()
				.getWindowToken(), 0);

	}

}// end Class
