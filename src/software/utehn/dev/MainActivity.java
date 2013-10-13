package software.utehn.dev;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	SQLiteDatabase db = null;

	void readDb() {
		String dir = Environment.getExternalStorageDirectory().getPath();
		File dbfile = new File(dir + File.separator + "hosxp.db");
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbfile, null);
		System.out.println("Its open? " + db.isOpen());
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
		readDb();

		// ���ҧ��� temp.db ������� android ���ҧ��ä���� database
		db = this.openOrCreateDatabase("temp.db",
				SQLiteDatabase.CREATE_IF_NECESSARY, null);
		db.close();

		Button btn_house = (Button) findViewById(R.id.btn_house);

		Button btn_pop = (Button) findViewById(R.id.btn_pop);

		Button btn_maps = (Button) findViewById(R.id.btn_maps);

		Button btn_note = (Button) findViewById(R.id.btn_note);

		btn_house.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {

				// List items
				final CharSequence[] items = { "���� 0 �.�ҵԵ�С��",
						"���� 1 �.�ҵԵ�С��", "���� 2 �.�ҵԵ�С��",
						"���� 3 �.�ҵԵ�С��", "���� 4 �.�ҵԵ�С��",
						"���� 5 �.�ҵԵ�С��", "���� 6 �.�ҵԵ�С��",
						"���� 7 �.�ҵԵ�С��", "���� 8 �.�ҵԵ�С��",

				};

				AlertDialog.Builder builder = new AlertDialog.Builder(
						MainActivity.this);

				builder.setTitle("���͡�����ҹ");

				builder.setSingleChoiceItems(items, -1,
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog, int pos) {

								Log.d("���͡", String.valueOf(items[pos]));

								Intent intent = new Intent(MainActivity.this,
										HouseActivity.class);

								startActivity(intent);

								dialog.dismiss();
							}
						});
				AlertDialog alert = builder.create();

				alert.show();

			}
		});

		btn_pop.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {

				// List items
				final CharSequence[] items = { "�硵�ӡ���1��", "��1-5��",
						"������¹", "��·ӧҹ", "����٧����", "˭ԧ��駤����",
						"������������ѧ", "���ԡ��" };

				AlertDialog.Builder builder = new AlertDialog.Builder(
						MainActivity.this);

				builder.setTitle("���͡������������");

				builder.setSingleChoiceItems(items, -1,
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog, int pos) {

								GlobalVar.TargetGroup = String
										.valueOf(items[pos]);
								Intent intent = new Intent(MainActivity.this,
										TargetGroupActivity.class);

								startActivity(intent);

								dialog.dismiss();
							}
						});
				AlertDialog alert = builder.create();

				alert.show();

			}
		});

		btn_maps.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {

			}
		});

		btn_note.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {

				Intent intent = new Intent(MainActivity.this,
						PersonDetailActivity.class);

				startActivity(intent);

			}
		});

	}// end onCreate

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		menu.add(0, 0, 0, "����Ң�����");
		menu.add(0, 1, 1, "���͡������");

		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case (0):

			AlertDialog.Builder alertbox = new AlertDialog.Builder(this);

			alertbox.setTitle("! ����͹");
			alertbox.setMessage("��ù���Ҩ�ź�����������ػ�ó���");

			alertbox.setPositiveButton("�׹�ѹ",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface arg0, int arg1) {
							new task_import().execute("hosxp.db");
						}
					});

			alertbox.setNegativeButton("¡��ԡ", null);

			alertbox.show();

			break;
		case (1):
			new task_export().execute("hosxp.db");
			break;
		}
		return false;
	}

	// task import
	class task_import extends AsyncTask<String, Integer, String> implements
			OnDismissListener {
		private ProgressDialog progressDialog = new ProgressDialog(
				MainActivity.this);

		InputStream src = null;
		OutputStream desc = null;

		String sdDir = Environment.getExternalStorageDirectory()
				.getAbsolutePath();

		String mPackage = getApplicationContext().getPackageName();
		String err_msg = "";

		protected void onPreExecute() {
			progressDialog.setOnDismissListener(this);
			progressDialog.setMessage("���ѧ�����...");
			progressDialog.show();

		}

		@Override
		protected String doInBackground(String... params) {
			try {

				src = new FileInputStream(sdDir + File.separator + params[0]);

				desc = new FileOutputStream("/data/data/" + mPackage
						+ "/databases/" + params[0]);

				byte[] buffer = new byte[1024];
				int read;
				while ((read = src.read(buffer)) != -1) {
					desc.write(buffer, 0, read);
				}

				src.close();
				desc.flush();
				desc.close();
				src = null;
				desc = null;
				return sdDir + File.separator + params[0];

			} catch (Exception e) {
				e.printStackTrace();
				err_msg = e.getMessage().toString();
				Log.d("Import", err_msg);
				return "err";
			}

		}

		protected void onPostExecute(String res) {
			if (res != "err") {
				this.progressDialog.hide();

				AlertDialog.Builder alertbox = new AlertDialog.Builder(
						MainActivity.this);
				alertbox.setMessage("����� �����");
				alertbox.setNeutralButton("Ok", null);
				alertbox.show();

			} else {
				this.progressDialog.hide();
				AlertDialog.Builder alertbox = new AlertDialog.Builder(
						MainActivity.this);
				alertbox.setMessage(err_msg);
				alertbox.setNeutralButton("Ok", null);
				alertbox.show();
			}
		}

		@Override
		public void onDismiss(DialogInterface dialog) {
			this.cancel(true);
		}
	}

	// end task import

	// task export

	class task_export extends AsyncTask<String, Integer, String> implements
			OnDismissListener {
		private ProgressDialog progressDialog = new ProgressDialog(
				MainActivity.this);

		InputStream src = null;
		OutputStream desc = null;

		String sdDir = Environment.getExternalStorageDirectory()
				.getAbsolutePath();

		String mPackage = getApplicationContext().getPackageName();
		String err_msg = "";

		protected void onPreExecute() {
			progressDialog.setOnDismissListener(this);
			progressDialog.setMessage("���ѧ���͡...");
			progressDialog.show();

		}

		@Override
		protected String doInBackground(String... params) {
			try {

				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss",
						getResources().getConfiguration().locale);
				String d = sdf.format(new Date());

				desc = new FileOutputStream(sdDir + File.separator + d + "_"
						+ params[0]);

				src = new FileInputStream("/data/data/" + mPackage
						+ "/databases/" + params[0]);

				byte[] buffer = new byte[1024];
				int read;
				while ((read = src.read(buffer)) != -1) {
					desc.write(buffer, 0, read);
				}

				src.close();
				desc.flush();
				desc.close();
				src = null;
				desc = null;
				return sdDir + File.separator + params[0];

			} catch (Exception e) {
				e.printStackTrace();
				err_msg = e.getMessage().toString();
				Log.d("Export", err_msg);
				return "err";
			}

		}

		protected void onPostExecute(String res) {
			if (res != "err") {
				this.progressDialog.hide();

				AlertDialog.Builder alertbox = new AlertDialog.Builder(
						MainActivity.this);
				alertbox.setMessage("���͡ �����");
				alertbox.setNeutralButton("Ok", null);
				alertbox.show();

			} else {
				this.progressDialog.hide();
				AlertDialog.Builder alertbox = new AlertDialog.Builder(
						MainActivity.this);
				alertbox.setMessage(err_msg);
				alertbox.setNeutralButton("Ok", null);
				alertbox.show();
			}
		}

		@Override
		public void onDismiss(DialogInterface dialog) {
			this.cancel(true);
		}
	}

	// end task export

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (db.isOpen())
			db.close();
		Log.d(this.getClass().getSimpleName(), "onDestroy");
	}

}// end Activity