package com.example.administrator.sportmeet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import android.support.v7.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class EntryActivity extends AppCompatActivity implements OnClickListener {
    EditText etSno, etName;
    RadioButton rbtnNan, rbtnNv;
    Button btnSubmit;
    Spinner spEvent;
    String[] eventNan = new String[] { "男子100米", "200米", "400米", "800米",
            "1500米", "5000米", "4*100米", "4*400米", "110栏", "跳高", "跳远", "三级跳",
            "铁饼", "铅球", "标枪" };
    String[] eventNv = new String[] { "女子100米", "200米", "400米", "800米",
            "1500米", "3000米", "4*100米", "4*400米", "110栏", "跳高", "跳远", "铁饼",
            "铅球", "标枪" };
    TextView tvEvent;
    String Sno, Name, Gender = "男", Event = "";
    String s="", select = "";
    Set set = new HashSet();
    List list = new LinkedList();
    boolean isSpinnerFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        PermisionUtils.verifyStoragePermissions(this);
        if (!new File(Environment.getExternalStorageDirectory().getPath() + "/Sno.txt").exists()) {
            System.out.println("文件Sno.txt不存在");
        }else{
            new File(Environment.getExternalStorageDirectory().getPath() + "/Sno.txt");
        }

        if (!new File(Environment.getExternalStorageDirectory().getPath() + "/Submit.txt").exists()) {
            System.out.println("文件Submit.txt不存在");
        }else{
            new File(Environment.getExternalStorageDirectory().getPath() + "/Submit.txt");
        }

        etSno = (EditText) this.findViewById(R.id.etSno);
        etSno.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                    try {
                        BufferedReader br = new BufferedReader(new FileReader(
                                Environment.getExternalStorageDirectory().getPath()
                                        + "/Sno.txt"));
                        String result = "";
                        while ((result = br.readLine()) != null)
                            list.add(result);
                        for (int i = 0; i < list.size(); i++) {
                            String str = (String) list.get(i);
                            if (str.equals(etSno.getText().toString())) {
                                Toast.makeText(EntryActivity.this, "同一学号只允许报名1次", Toast.LENGTH_SHORT).show();
                                etSno.setText("");
                            }
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {

            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {

            }
        });

        etName = (EditText) this.findViewById(R.id.etName);

        rbtnNan = (RadioButton) this.findViewById(R.id.rbtnNan);
        rbtnNan.setOnClickListener(this);
        rbtnNv = (RadioButton) this.findViewById(R.id.rbtnNv);
        rbtnNv.setOnClickListener(this);

        tvEvent = (TextView) this.findViewById(R.id.tvEvent);
        tvEvent.setText("");

        spEvent = (Spinner) this.findViewById(R.id.spEvent);
        ArrayAdapter adapterNan = new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item, eventNan);
        spEvent.setAdapter(adapterNan);
        spEvent.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> av, View v, int position,
                                       long arg3) {
                if (isSpinnerFirst) {
                    // 使spinner的第1项不会被默认选中
                    isSpinnerFirst = false;
                    return;
                }

                Event = av.getItemAtPosition(position).toString();
                set.add(Event);
                select += Event + "\r\n";
                if (set.size() > 2) {
                    Toast.makeText(EntryActivity.this, "报名项目最多为两项", Toast.LENGTH_SHORT).show();
                    set.remove(Event);
                } else {
                    tvEvent.setText(select);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        registerForContextMenu(tvEvent);

        btnSubmit = (Button) this.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { // 使用此方法调用OptionsMenu
        getMenuInflater().inflate(R.menu.entry, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { // 选中菜单项后发生的动作
        switch (item.getItemId()) {
            case R.id.menu_exit:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("确定要退出系统吗？").setTitle("退出确认")
                        .setIcon(R.drawable.ok);

                builder.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                System.exit(0);
                            }
                        });

                builder.setNegativeButton("放弃",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();

                break;
            case R.id.menu_cancel:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, 0, 0, "清空");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                select="";
                tvEvent.setText("");
                set.clear();
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onContextMenuClosed(Menu menu) {
        super.onContextMenuClosed(menu);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rbtnNan:
                isSpinnerFirst=true;
                if (rbtnNan.isChecked()) {
                    Gender = "男";
                    ArrayAdapter adapterNan = new ArrayAdapter(this,
                            android.R.layout.simple_spinner_dropdown_item, eventNan);
                    spEvent.setAdapter(adapterNan);
                }
                break;
            case R.id.rbtnNv:
                isSpinnerFirst=true;
                if (rbtnNv.isChecked()) {
                    Gender = "女";
                    ArrayAdapter adapterNv = new ArrayAdapter(this,
                            android.R.layout.simple_spinner_dropdown_item, eventNv);
                    spEvent.setAdapter(adapterNv);
                }
                break;
            case R.id.btnSubmit:
                try {
                    FileWriter fw = new FileWriter(Environment
                            .getExternalStorageDirectory().getPath() + "/Sno.txt",
                            true);
                    String s = etSno.getText().toString() + "\r\n";
                    fw.write(s);
                    fw.flush();
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    FileWriter fw = new FileWriter(Environment
                            .getExternalStorageDirectory().getPath()
                            + "/Submit.txt", true);
                    String s = etSno.getText().toString() + ","
                            + etName.getText().toString() + "," + Gender + ","
                            + select + "\r\n";
                    fw.write(s);
                    fw.flush();
                    fw.close();
                    Toast.makeText(this, "写文件完成！", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                etSno.setText("");
                etName.setText("");
                tvEvent.setText("");
                s="";
                Event="";
                select="";
                set.clear();
                break;
        }
    }
}
