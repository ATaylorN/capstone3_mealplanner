import { initializeApp } from "firebase/app";
import { getAnalytics } from "firebase/analytics";
import { getStorage } from "firebase/storage";
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
const firebaseConfig = {
  apiKey: "AIzaSyB-fKR0FaaVGQhCubKkpMQZxM7mbntHB4w",
  authDomain: "capstone-meal.firebaseapp.com",
  projectId: "capstone-meal",
  messagingSenderId: "873102642159",
  appId: "1:873102642159:web:2423cdd3c8bfef7414841a",
  measurementId: "G-RPT693PW5Y",
  storageBucket: 'gs://capstone-meal.appspot.com'
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
const analytics = getAnalytics(app);
const fireBaseStorage = getStorage(app);

export { fireBaseStorage, analytics }