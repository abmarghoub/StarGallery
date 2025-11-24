package stor.ens.ma.stor.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import stor.ens.ma.stor.R;
import stor.ens.ma.stor.adapter.StarAdapter;
import stor.ens.ma.stor.service.StarService;

public class ListActivity extends AppCompatActivity {

    private StarAdapter starAdapter; // <-- correct name

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Real Madrid Legend");
        RecyclerView rv = findViewById(R.id.recycle_view); // ID du XML
        rv.setLayoutManager(new LinearLayoutManager(this));

        // Initialisation de l'adapter avec les données du service
        starAdapter = new StarAdapter(this, StarService.getInstance().findAll());
        rv.setAdapter(starAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setQueryHint("Rechercher un joueur...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                starAdapter.getFilter().filter(query); // <-- corrige ici
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                starAdapter.getFilter().filter(newText); // <-- corrige ici
                return false;
            }
        });

        return true;
    }

    // Suppression de onOptionsItemSelected car le prof ne veut pas de "share"
}
