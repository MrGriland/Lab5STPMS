package bgv.fit.bstu.lab5stpms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity3 extends AppCompatActivity {
    Recipe recipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Bundle arguments = getIntent().getExtras();
        if(arguments!=null)
        {
            recipe = (Recipe) arguments.get("Recipe");
            TextView textView = findViewById(R.id.infotv);
            textView.setText(recipe.name+" "+recipe.category+" "+recipe.ingredients+" "+recipe.time+" "+recipe.recipe);
        }
    }
}