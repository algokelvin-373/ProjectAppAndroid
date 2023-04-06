package algokelvin.app.paginationjava.model;

/**
 * Created by K. A. ANUSHKA MADUSANKA on 7/9/2018.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieDBResponse implements Parcelable
{

    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("total_results")
    @Expose
    private Integer totalMovies;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    @SerializedName("results")
    @Expose
    private List<Movie> Movies = null;
    public final static Creator<MovieDBResponse> CREATOR = new Creator<MovieDBResponse>() {


        @SuppressWarnings({
                "unchecked"
        })
        public MovieDBResponse createFromParcel(Parcel in) {
            return new MovieDBResponse(in);
        }

        public MovieDBResponse[] newArray(int size) {
            return (new MovieDBResponse[size]);
        }

    }
            ;

    protected MovieDBResponse(Parcel in) {
        this.page = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.totalMovies = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.totalPages = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.Movies, (com.androidtutz.anushka.tmdbclient.model.Movie.class.getClassLoader()));
    }

    public MovieDBResponse() {
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalMovies() {
        return totalMovies;
    }

    public void setTotalMovies(Integer totalMovies) {
        this.totalMovies = totalMovies;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<Movie> getMovies() {
        return Movies;
    }

    public void setMovies(List<Movie> Movies) {
        this.Movies = Movies;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(page);
        dest.writeValue(totalMovies);
        dest.writeValue(totalPages);
        dest.writeList(Movies);
    }

    public int describeContents() {
        return 0;
    }

}