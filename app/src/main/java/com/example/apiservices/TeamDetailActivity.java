package com.example.apiservices;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class TeamDetailActivity extends AppCompatActivity {
    private static final String TAG = "TeamDetailActivity";
    public static final String EXTRA_TEAM_ID = "team_id";
    private static final String API_KEY = "3101ff0dfc3b499d9caed180243f64e2";
    private static final String BASE_URL = "https://api.football-data.org/v4/teams/";

    private TextView teamNameTextView;
    private TextView teamShortNameTextView;
    private TextView teamInfoTextView;
    private RecyclerView competitionsRecyclerView;
    private RecyclerView squadRecyclerView;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_detail);

        // Inicializar vistas
        teamNameTextView = findViewById(R.id.teamName);
        teamShortNameTextView = findViewById(R.id.teamShortName);
        teamInfoTextView = findViewById(R.id.teamInfo);
        competitionsRecyclerView = findViewById(R.id.competitionsRecyclerView);
        squadRecyclerView = findViewById(R.id.squadRecyclerView);

        // Configurar RecyclerViews
        competitionsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        squadRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar Volley
        requestQueue = Volley.newRequestQueue(this);
        imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
            @Override
            public void putBitmap(String url, android.graphics.Bitmap bitmap) {}
            @Override
            public android.graphics.Bitmap getBitmap(String url) { return null; }
        });

        // Obtener el ID del equipo del intent
        String teamId = getIntent().getStringExtra(EXTRA_TEAM_ID);
        if (teamId != null) {
            loadTeamDetails(teamId);
        } else {
            Toast.makeText(this, "Error: No se proporcionó ID del equipo", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void loadTeamDetails(String teamId) {
        String url = BASE_URL + teamId;
        Log.d(TAG, "Cargando detalles del equipo desde: " + url);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        Log.d(TAG, "Respuesta recibida: " + response.toString());
                        
                        // Cargar datos básicos del equipo
                        teamNameTextView.setText(response.getString("name"));
                        teamShortNameTextView.setText(response.getString("shortName"));

                        // Construir información del equipo
                        StringBuilder info = new StringBuilder();
                        if (response.has("area")) {
                            JSONObject area = response.getJSONObject("area");
                            info.append("País: ").append(area.getString("name")).append("\n");
                        }
                        if (response.has("address")) {
                            info.append("Dirección: ").append(response.getString("address")).append("\n");
                        }
                        if (response.has("website")) {
                            info.append("Web: ").append(response.getString("website")).append("\n");
                        }
                        if (response.has("founded")) {
                            info.append("Fundado: ").append(response.getString("founded")).append("\n");
                        }
                        if (response.has("clubColors")) {
                            info.append("Colores: ").append(response.getString("clubColors")).append("\n");
                        }
                        if (response.has("venue")) {
                            info.append("Estadio: ").append(response.getString("venue"));
                        }
                        teamInfoTextView.setText(info.toString());

                        // Cargar competiciones
                        if (response.has("runningCompetitions")) {
                            ArrayList<TeamDetail.Competition> competitions = new ArrayList<>();
                            for (int i = 0; i < response.getJSONArray("runningCompetitions").length(); i++) {
                                JSONObject comp = response.getJSONArray("runningCompetitions").getJSONObject(i);
                                TeamDetail.Competition competition = new TeamDetail.Competition();
                                competition.id = comp.getInt("id");
                                competition.name = comp.getString("name");
                                competition.code = comp.getString("code");
                                competition.type = comp.getString("type");
                                competition.emblem = comp.isNull("emblem") ? null : comp.getString("emblem");
                                competitions.add(competition);
                            }
                            competitionsRecyclerView.setAdapter(new CompetitionAdapter(TeamDetailActivity.this, competitions, imageLoader));
                        }

                        // Cargar plantilla
                        if (response.has("squad")) {
                            ArrayList<TeamDetail.Player> players = new ArrayList<>();
                            for (int i = 0; i < response.getJSONArray("squad").length(); i++) {
                                JSONObject player = response.getJSONArray("squad").getJSONObject(i);
                                TeamDetail.Player squadPlayer = new TeamDetail.Player();
                                squadPlayer.id = player.getInt("id");
                                squadPlayer.name = player.getString("name");
                                squadPlayer.position = player.getString("position");
                                squadPlayer.dateOfBirth = player.getString("dateOfBirth");
                                squadPlayer.nationality = player.getString("nationality");
                                players.add(squadPlayer);
                            }
                            squadRecyclerView.setAdapter(new PlayerAdapter(TeamDetailActivity.this, players));
                        }

                    } catch (JSONException e) {
                        Log.e(TAG, "Error al procesar JSON: " + e.getMessage(), e);
                        Toast.makeText(TeamDetailActivity.this, "Error al procesar los datos: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    String errorMessage = "Error desconocido";
                    if (error.networkResponse != null) {
                        int statusCode = error.networkResponse.statusCode;
                        String responseData = new String(error.networkResponse.data);
                        errorMessage = "Error " + statusCode + ": " + responseData;
                        Log.e(TAG, "Error de red: " + errorMessage);
                    } else if (error.getCause() != null) {
                        errorMessage = "Error de conexión: " + error.getCause().getMessage();
                        Log.e(TAG, "Error de conexión", error.getCause());
                    }
                    Toast.makeText(TeamDetailActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                }
            }) {
            @Override
            public java.util.Map<String, String> getHeaders() {
                java.util.Map<String, String> headers = new java.util.HashMap<>();
                headers.put("X-Auth-Token", API_KEY);
                return headers;
            }
        };

        requestQueue.add(request);
    }
} 