package eu.epfc.pocketmovie;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import eu.epfc.pocketmovie.model.Film;

/**
 * Created by 0107anocchilupo on 14/05/2018.
 */

class SWFilmsAdapter extends RecyclerView.Adapter<SWFilmsAdapter.FilmViewHolder>{

    public interface ListItemClickListener{
        void onListItemClick(int clickedItemIndex);
    }

    final private ListItemClickListener listItemClickListener;
    protected List<Film> films;


    SWFilmsAdapter(ListItemClickListener listItemClickListener) {
        this.listItemClickListener = listItemClickListener;
    }

    public void setFilms(List<Film> films){
        this.films = films;

        this.notifyDataSetChanged();
    }

    @Override
    public FilmViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View filmView = layoutInflater.inflate(R.layout.item_film,parent,false);

        return new FilmViewHolder(filmView);
    }

    @Override
    public int getItemCount() {
        return (films == null) ? 0 : films.size();
    }


    @Override
    public void onBindViewHolder(FilmViewHolder holder, int position) {
        Log.d(this.getClass().getName(), "BEFORE TRY CATCH ");

        boolean isLast = (position == films.size() && position != 0);

        ViewGroup viewGroup = (ViewGroup) holder.itemView;
        TextView title = viewGroup.findViewById(R.id.text_app_name);
        TextView rating = viewGroup.findViewById(R.id.text_title);
        ImageView poster = viewGroup.findViewById(R.id.image_poster);
        if(films.size() != 0) {
            Film film = films.get(position);
            title.setText(film.getTitle());
            rating.setText(viewGroup.getContext().getResources().getString(R.string.rating) + String.valueOf(film.getRate()));

            PMHelper.setImageView(poster, film.getPoster());
            Log.d(this.getClass().getName(), "END TRY CATCH ");
        }
    }


    public class FilmViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        public FilmViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickPosition = getAdapterPosition();
            SWFilmsAdapter.this.listItemClickListener.onListItemClick(clickPosition);
        }
    }
}
