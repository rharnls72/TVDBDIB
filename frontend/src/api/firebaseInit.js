import firebase from 'firebase'
import 'firebase/firestore'
var firebaseConfig = {
    apiKey: "AIzaSyAVLHmJoWa3tY3Xz30ub9eUjeSFG2yGlNU",
    authDomain: "tvility.firebaseapp.com",
    databaseURL: "https://tvility.firebaseio.com",
    projectId: "tvility",
    storageBucket: "tvility.appspot.com"
};
const firebaseApp = firebase.initializeApp(firebaseConfig)
export default firebaseApp.firestore()