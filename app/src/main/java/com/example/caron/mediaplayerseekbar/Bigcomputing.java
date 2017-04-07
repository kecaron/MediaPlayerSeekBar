package com.example.caron.mediaplayerseekbar;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.SeekBar;

/**
 * Created by CARON on 07/04/2017.
 */

public class Bigcomputing extends AsyncTask<Void, Integer, Void> {
    Context mContext;
    ProgressBar mProgressBar;
    MediaPlayer mediaPlayer;

    public Bigcomputing(Context c, SeekBar p, MediaPlayer mediaPlayer)
    {
        mContext = c;
        mProgressBar = p;
        this.mediaPlayer = mediaPlayer;
    }

    // Invoquée par la thread UI avant l’exécution de la tâche.
    // On la configure/initialise ici.
    @Override
    protected void onPreExecute() {
        /*Toast.makeText(mContext, "OnPreExecute",
                Toast.LENGTH_SHORT).show();*/
        super.onPreExecute();
    }

    // Invoquée après la fin de l’exécution doInBackground(Params...).
    // Le résultat de doInBackground(Params...) est passé comme paramètre cette méthode.
    // Ce code est exécuté sur le thread UI.
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
    // Invoquée après un appel à publishProgress(Progress...) pour mettre à jour l’UI (asynchrone).
    // C’est le code qui permet de mettre à jour l’affichage de la progression (ProgressBar, …).
    // Ce code est exécuté sur le thread UI.

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        mProgressBar.setProgress(values[0]);
    }

    // Invoquée sur l’instance après onPreExecute().
    // Le coeur de la tâche. Les paramètres sont récupérés ici (par ex. une liste d’URL).
    // Cette méthode peut utiliser publishProgress(Progress...) pour publier la progression.

    @Override
    protected Void doInBackground(Void... params) {
        try {

                while (this.mediaPlayer.getCurrentPosition() < this.mediaPlayer.getDuration()) {
                    // Thread sleep pour dire que la seek bar sera updated après 3 sec --> le son ne bug plus trop dans l'emulateur.
                    publishProgress(this.mediaPlayer.getCurrentPosition());
                    Thread.currentThread().sleep(4000);
                }
        }
            catch (Exception e) {
            }



        return null;
    }
}
