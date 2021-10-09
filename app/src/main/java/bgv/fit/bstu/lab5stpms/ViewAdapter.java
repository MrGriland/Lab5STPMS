package bgv.fit.bstu.lab5stpms;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.ViewHolder>{

    private static MainActivity context;

    interface OnStateClickListener{
        void onStateClick(Recipe recipe, int position);
    }
    private final OnStateClickListener onClickListener;

    private final LayoutInflater inflater;
    private final List<Recipe> recipes;

    ViewAdapter(Context context, List<Recipe> recipes, OnStateClickListener onClickListener) {
        this.context = (MainActivity) context;
        this.onClickListener = onClickListener;
        this.recipes = recipes;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        //holder.flagView.setImageResource(recipe.getFlagResource());
        holder.nameView.setText(recipe.name);
        holder.categoryView.setText(recipe.category);

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                onClickListener.onStateClick(recipe, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        final ImageView flagView;
        final TextView nameView, categoryView;
        ViewHolder(View view){
            super(new RecyclerContextMenuInfoWrapperView(view));
            ((RecyclerContextMenuInfoWrapperView)itemView).setHolder(this);
            context.registerForContextMenu(itemView);
            flagView = (ImageView)view.findViewById(R.id.flag);
            nameView = (TextView) view.findViewById(R.id.nametv);
            categoryView = (TextView) view.findViewById(R.id.categorytv);
        }

    }

}
