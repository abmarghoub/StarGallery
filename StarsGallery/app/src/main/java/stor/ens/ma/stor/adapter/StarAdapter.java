package stor.ens.ma.stor.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import stor.ens.ma.stor.R;
import stor.ens.ma.stor.beans.Star;
import stor.ens.ma.stor.service.StarService;

public class StarAdapter extends RecyclerView.Adapter<StarAdapter.StarViewHolder> implements Filterable {

    private List<Star> stars;        // liste complète
    private List<Star> starsFilter;  // liste affichée (après filtre)
    private Context context;
    private NewFilter mfilter;

    public StarAdapter(Context context, List<Star> stars) {
        this.context = context;
        this.stars = stars;
        this.starsFilter = new ArrayList<>(stars);
        this.mfilter = new NewFilter(this);
    }

    @NonNull
    @Override
    public StarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.star_item, parent, false);
        final StarViewHolder holder = new StarViewHolder(v);

        // Popup pour éditer la note
        holder.itemView.setOnClickListener(view -> {
            int position = holder.getAdapterPosition();
            if (position == RecyclerView.NO_POSITION) return;

            Star star = starsFilter.get(position);

            View popup = LayoutInflater.from(context).inflate(R.layout.star_edit_item, null, false);
            CircleImageView imgPopup = popup.findViewById(R.id.imgPopup);
            RatingBar bar = popup.findViewById(R.id.ratingBar);

            Glide.with(context).load(star.getImg()).into(imgPopup);
            bar.setRating(star.getRating());

            AlertDialog dialog = new AlertDialog.Builder(context)
                    .setTitle("Notez :")
                    .setMessage("Donner une note entre 1 et 5 :")
                    .setView(popup)
                    .setNegativeButton("Annuler", null)
                    .setPositiveButton("Valider", (d, which) -> {
                        float newRating = bar.getRating();
                        star.setRating(newRating);
                        StarService.getInstance().update(star);
                        notifyItemChanged(position);
                    })
                    .create();

            dialog.show();

            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#FF4500")); // rouge orangé
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#4B2E05")); // marron foncé
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull StarViewHolder holder, int position) {
        Star s = starsFilter.get(position);

        Glide.with(context)
                .load(s.getImg())
                .into(holder.img);

        holder.name.setText(s.getName());
        holder.rating.setRating(s.getRating());

        holder.btnDelete.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos == RecyclerView.NO_POSITION) return;

            Star star = starsFilter.get(pos);

            AlertDialog deleteDialog = new AlertDialog.Builder(context)
                    .setTitle("Supprimer")
                    .setMessage("Voulez-vous supprimer " + star.getName() + " ?")
                    .setPositiveButton("Oui", (dialog, which) -> {
                        StarService.getInstance().delete(star); // Supprimer du service
                        starsFilter.remove(pos);                  // Supprimer de la liste filtrée
                        stars.remove(star);                        // Supprimer de la liste originale
                        notifyItemRemoved(pos);                    // Rafraîchir RecyclerView
                        notifyItemRangeChanged(pos, starsFilter.size());
                    })
                    .setNegativeButton("Non", null)
                    .create();

            deleteDialog.show();

            // Changer la couleur des boutons
            deleteDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                    .setTextColor(Color.parseColor("#FF4500")); // rouge/orangé
            deleteDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                    .setTextColor(Color.parseColor("#4B2E05")); // marron foncé
        });
    }

    @Override
    public int getItemCount() {
        return starsFilter.size();
    }

    @Override
    public Filter getFilter() {
        return mfilter;
    }

    static class StarViewHolder extends RecyclerView.ViewHolder {
        CircleImageView img;
        TextView name;
        RatingBar rating;
        ImageView btnDelete;

        StarViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgStar);
            name = itemView.findViewById(R.id.tvName);
            rating = itemView.findViewById(R.id.rating);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

    // ================= FILTRE =================
    public class NewFilter extends Filter {
        private RecyclerView.Adapter mAdapter;

        public NewFilter(RecyclerView.Adapter mAdapter) {
            this.mAdapter = mAdapter;
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Star> filtered = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filtered.addAll(stars);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (Star p : stars) {
                    // on cherche n'importe quelle partie du nom
                    if (p.getName().toLowerCase().contains(filterPattern)) {
                        filtered.add(p);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filtered;
            results.count = filtered.size();
            return results;
        }


        @Override
        @SuppressWarnings("unchecked")
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            starsFilter = (List<Star>) filterResults.values;
            mAdapter.notifyDataSetChanged();
        }
    }

}
