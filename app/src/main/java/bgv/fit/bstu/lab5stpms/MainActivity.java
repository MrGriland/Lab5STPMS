package bgv.fit.bstu.lab5stpms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    ArrayList<Recipe> recipes = new ArrayList<>();
    public int mCurrentItemPosition;
    Intent intentview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intentview = new Intent(this, MainActivity3.class);
        setInitialData();
        Adapter();
    }

    public void Adapter()
    {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.relist);
        ViewAdapter.OnStateClickListener stateClickListener = new ViewAdapter.OnStateClickListener() {
            @Override
            public void onStateClick(Recipe recipe, int position) {

                Toast.makeText(getApplicationContext(), "Был выбран пункт " + recipe.name,
                        Toast.LENGTH_SHORT).show();
                intentview.putExtra("Recipe", recipe);
                startActivity(intentview);
            }
            // создаем адаптер
        };
        ViewAdapter adapter = new ViewAdapter(this, recipes, stateClickListener);
        // устанавливаем для списка адаптер
        recyclerView.setAdapter(adapter);
    }

    private void setInitialData() {
        File myFile = new File(getFilesDir().toString() + "/" + "5LabSTPMS.json");
        if(myFile.exists())
            recipes = Read();
        else {
            try {
                myFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Recipe> Read()
    {
        File myFile = new File(getFilesDir().toString() + "/" + "5LabSTPMS.json");
        ArrayList<Recipe> recipes = new ArrayList<Recipe>();
        try {
            FileInputStream inputStream = new FileInputStream(myFile);
            /*
             * Буфферезируем данные из выходного потока файла
             */
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            /*
             * Класс для создания строк из последовательностей символов
             */
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            try {
                /*
                 * Производим построчное считывание данных из файла в конструктор строки,
                 * Псоле того, как данные закончились, производим вывод текста в TextView
                 */
                while ((line = bufferedReader.readLine()) != null){
                    stringBuilder.append(line);
                }
                Gson gson = new Gson();
                DataItems dataItems = gson.fromJson(stringBuilder.toString(), DataItems.class);
                try {
                    if (dataItems.getRecipes() != null) {
                        for (Recipe recipe :
                                dataItems.getRecipes()) {
                            recipes.add(recipe);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return recipes;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.action_create):
                Toast.makeText(getApplicationContext(), "Был выбран пункт create",
                        Toast.LENGTH_SHORT).show();
                Intent add = new Intent(this, MainActivity2.class);
                startActivity(add);
                break;
            case (R.id.action_asc):
                Toast.makeText(getApplicationContext(), "Был выбран пункт asc",
                        Toast.LENGTH_SHORT).show();
                Collections.sort(recipes);
                Adapter();
                break;
            case (R.id.action_desc):
                Toast.makeText(getApplicationContext(), "Был выбран пункт desc",
                        Toast.LENGTH_SHORT).show();
                Comparator desc = new DescComparator();
                Collections.sort(recipes, desc);
                Adapter();
                break;
            case (R.id.action_update):
                Toast.makeText(getApplicationContext(), "Был выбран пункт update",
                        Toast.LENGTH_SHORT).show();
                setInitialData();
                Adapter();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position = ((RecyclerContextMenuInfoWrapperView.RecyclerContextMenuInfo)item.getMenuInfo()).position;
        Toast.makeText(getApplicationContext(), " User selected  " + String.valueOf(position), Toast.LENGTH_LONG).show();
        switch (item.getItemId()) {
            case R.id.edit:
                Toast.makeText(getApplicationContext(), "Был выбран пункт create",
                        Toast.LENGTH_SHORT).show();

                return true;
            case R.id.delete:
                Toast.makeText(getApplicationContext(), "Был выбран пункт del",
                        Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setMessage("Вы уверены?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { // Кнопка ОК
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Recipe recipe = recipes.get(position);
                        recipes.remove(recipe);
                        Rewrite();
                        Adapter();
                        dialog.dismiss(); // Отпускает диалоговое окно
                    }
                });
                builder.setNegativeButton("Oтмена", new DialogInterface.OnClickListener() { // Кнопка ОК
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); // Отпускает диалоговое окно
                    }
                });
                builder.create();
                builder.show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void Rewrite()
    {
        File myFile = new File(getFilesDir().toString() + "/" + "5LabSTPMS.json");
        Gson gson = new Gson();
        DataItems dataItems = new DataItems();
        dataItems.setRecipes(recipes);
        String jsonString = gson.toJson(dataItems);

        try {
            FileOutputStream outputStream = new FileOutputStream(myFile);
            /*
             * Буфферезируем данные из выходного потока файла
             */
            outputStream.write(jsonString.getBytes());
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}