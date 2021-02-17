package com.example.tp04listview;

public class PeliculaExtensa {
    private String _Title;
    private String _imdbID;
    private String _Rated;
    private String _Runtime;
    private String _Year;
    private String _Actors;
    private String _Plot;
    private String _Awards;
    private String _Poster;

    public PeliculaExtensa(String title, String year, String ImdbID, String poster) {
        _Title = title;
        _imdbID = ImdbID;
        _Year = year;
        _Poster = poster;
    }
    public PeliculaExtensa(String title, String rated, String runtime, String year, String actors, String plot, String awards, String poster) {
        _Title = title;
        _Rated = rated;
        _Runtime = runtime;
        _Year = year;
        _Actors = actors;
        _Plot = plot;
        _Awards = awards;
        _Poster = poster;
    }

    public String get_imdbID() {
        return _imdbID;
    }

    public void set_imdbID(String _imdbID) {
        this._imdbID = _imdbID;
    }

    public String get_Awards() {
        return _Awards;
    }

    public void set_Awards(String _Awards) {
        this._Awards = _Awards;
    }

    public String get_Runtime() {
        return _Runtime;
    }

    public void set_Runtime(String _Runtime) {
        this._Runtime = _Runtime;
    }

    public String get_Rated() {
        return _Rated;
    }

    public void set_Rated(String _Rated) {
        this._Rated = _Rated;
    }

    public String get_Title() {
        return _Title;
    }

    public void set_Title(String _Title) {
        this._Title = _Title;
    }

    public String get_Year() {
        return _Year;
    }

    public void set_Year(String _Year) {
        this._Year = _Year;
    }

    public String get_Actors() {
        return _Actors;
    }

    public void set_Actors(String _Actors) {
        this._Actors = _Actors;
    }

    public String get_Plot() {
        return _Plot;
    }

    public void set_Plot(String _Plot) {
        this._Plot = _Plot;
    }

    public String get_Poster() {
        return _Poster;
    }

    public void set_Poster(String _Poster) {
        this._Poster = _Poster;
    }
}
