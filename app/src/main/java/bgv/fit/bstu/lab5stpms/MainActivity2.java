package bgv.fit.bstu.lab5stpms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    ArrayList<Recipe> recipes = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    public void SaveRecipe(View view)
    {
        try {
            recipes = Read();
        }
        catch (Exception e){}
        EditText name = findViewById(R.id.nameet);
        EditText category = findViewById(R.id.catet);
        EditText ingredients = findViewById(R.id.inget);
        EditText recipec = findViewById(R.id.recet);
        EditText time = findViewById(R.id.timeet);
        Recipe recipe = new Recipe();
        recipe.name = name.getText().toString();
        recipe.category = category.getText().toString();
        recipe.ingredients = ingredients.getText().toString();
        recipe.recipe = recipec.getText().toString();
        recipe.time = time.getText().toString();
        recipes.add(recipe);
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
}