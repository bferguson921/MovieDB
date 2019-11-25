
package com.example.moviedb.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import kotlinx.android.parcel.Parcelize;

public class Images implements Parcelable{

    @SerializedName("base_url")
    @Expose
    private String baseUrl;
    @SerializedName("secure_base_url")
    @Expose
    private String secureBaseUrl;
    @SerializedName("backdrop_sizes")
    @Expose
    private List<String> backdropSizes = null;
    @SerializedName("logo_sizes")
    @Expose
    private List<String> logoSizes = null;
    @SerializedName("poster_sizes")
    @Expose
    private List<String> posterSizes = null;
    @SerializedName("profile_sizes")
    @Expose
    private List<String> profileSizes = null;
    @SerializedName("still_sizes")
    @Expose
    private List<String> stillSizes = null;

    public Images(Parcel in) {
        baseUrl = in.readString();
        secureBaseUrl = in.readString();
        backdropSizes = in.createStringArrayList();
        logoSizes = in.createStringArrayList();
        posterSizes = in.createStringArrayList();
        profileSizes = in.createStringArrayList();
        stillSizes = in.createStringArrayList();
    }

    public static final Parcelable.Creator<Images> CREATOR = new Parcelable.Creator<Images>() {
        @Override
        public Images createFromParcel(Parcel parcel) {
            return new Images(parcel);
        }

        @Override
        public Images[] newArray(int size) {
            return new Images[size];
        }
    };

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getSecureBaseUrl() {
        return secureBaseUrl;
    }

    public void setSecureBaseUrl(String secureBaseUrl) {
        this.secureBaseUrl = secureBaseUrl;
    }

    public List<String> getBackdropSizes() {
        return backdropSizes;
    }

    public void setBackdropSizes(List<String> backdropSizes) {
        this.backdropSizes = backdropSizes;
    }

    public List<String> getLogoSizes() {
        return logoSizes;
    }

    public void setLogoSizes(List<String> logoSizes) {
        this.logoSizes = logoSizes;
    }

    public List<String> getPosterSizes() {
        return posterSizes;
    }

    public void setPosterSizes(List<String> posterSizes) {
        this.posterSizes = posterSizes;
    }

    public List<String> getProfileSizes() {
        return profileSizes;
    }

    public void setProfileSizes(List<String> profileSizes) {
        this.profileSizes = profileSizes;
    }

    public List<String> getStillSizes() {
        return stillSizes;
    }

    public void setStillSizes(List<String> stillSizes) {
        this.stillSizes = stillSizes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(baseUrl);
        parcel.writeString(secureBaseUrl);
        parcel.writeStringList(backdropSizes);
        parcel.writeStringList(logoSizes);
        parcel.writeStringList(posterSizes);
        parcel.writeStringList(profileSizes);
        parcel.writeStringList(stillSizes);
    }
}
