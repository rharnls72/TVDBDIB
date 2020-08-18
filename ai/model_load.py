import pandas as pd
import numpy as np
import tensorflow as tf
import sys

def model_load(user_id):

    ratings_file = "/tvility/testData.csv"
    # ratings_file = "testData.csv"
    df = pd.read_csv(ratings_file)

    user_ids = df["userId"].unique().tolist()
    user2user_encoded = {x: i for i, x in enumerate(user_ids)}
    movie_ids = df["programId"].unique().tolist()
    movie2movie_encoded = {x: i for i, x in enumerate(movie_ids)}
    movie_encoded2movie = {i: x for i, x in enumerate(movie_ids)}

    df = df.sample(frac=1, random_state=42)

    model = tf.keras.models.load_model("/tvility/test_model")
    movie_df = pd.read_csv(ratings_file)

    movies_watched_by_user = df[df.userId == user_id]
    movies_not_watched = movie_df[
        ~movie_df["programId"].isin(movies_watched_by_user.programId.values)
    ]["programId"]
    movies_not_watched = list(
        set(movies_not_watched).intersection(set(movie2movie_encoded.keys()))
    )
    movies_not_watched = [[movie2movie_encoded.get(x)] for x in movies_not_watched]
    user_encoder = user2user_encoded.get(user_id)
    user_movie_array = np.hstack(
        ([[user_encoder]] * len(movies_not_watched), movies_not_watched)
    )

    user_movie_array = user_movie_array.astype(np.int64)
    ratings = model.predict(user_movie_array).flatten()

    top_ratings_indices = ratings.argsort()[-20:][::-1]
    recommended_movie_ids = [
        movie_encoded2movie.get(movies_not_watched[x][0]) for x in top_ratings_indices
    ]
    
    print (recommended_movie_ids)

    return recommended_movie_ids

# model_load(int(sys.argv[1]))