package id.aldiansyah.moviecataloguesub5;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import id.aldiansyah.moviecataloguesub5.search.movies.SearchActivity;
import id.aldiansyah.moviecataloguesub5.search.tvShow.SearchTvShowActivity;
import id.aldiansyah.moviecataloguesub5.setting.SettingActivity;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;
    String typeMovies = "moviesdb";
    String typeTvShow = "tvshowdb";
    AppBarConfiguration appBarConfiguration;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);
        toolbar = findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_movies, R.id.navigation_tvShow, R.id.navigation_favorite)
                .build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_setting) {
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_movie:
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.search_input, null);
                dialog.setView(view);
                dialog.setCancelable(true);
                dialog.setIcon(R.drawable.ic_search_black_24dp);
                dialog.setTitle("Search Movies");

                final EditText edtcari = view.findViewById(R.id.cari);

                dialog.setPositiveButton("SEARCH", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String cariMovies = edtcari.getText().toString().trim();
                        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                        intent.putExtra(SearchActivity.EXTRA_TYPE, typeMovies);
                        intent.putExtra(SearchActivity.EXTRA_QUERY, cariMovies);
                        startActivity(intent);
                    }
                });

                dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;

            case R.id.search_tvShow:
                AlertDialog.Builder dialogTvShow = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflaterTvShow = getLayoutInflater();
                View viewTvShow = inflaterTvShow.inflate(R.layout.search_input, null);
                dialogTvShow.setView(viewTvShow);
                dialogTvShow.setCancelable(true);
                dialogTvShow.setIcon(R.drawable.ic_search_black_24dp);
                dialogTvShow.setTitle("Search Movies");

                final EditText edtcariTvShow = viewTvShow.findViewById(R.id.cari);

                dialogTvShow.setPositiveButton("SEARCH", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogTvShow, int which) {
                        String serachTvShow = edtcariTvShow.getText().toString().trim();
                        Intent intent = new Intent(MainActivity.this, SearchTvShowActivity.class);
                        intent.putExtra(SearchTvShowActivity.EXTRA_TYPE, typeTvShow);
                        intent.putExtra(SearchTvShowActivity.EXTRA_QUERY, serachTvShow);
                        startActivity(intent);
                    }
                });

                dialogTvShow.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogTvShow, int which) {
                        dialogTvShow.dismiss();
                    }
                });
                dialogTvShow.show();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
