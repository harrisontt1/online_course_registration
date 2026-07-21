/*
 * 
 * Logout JavaScript
 * Online Course Registration System
 *
 * This file handles user logout by clearing session storage and
 * returning the user to the login page.
 * 
 */

function logoutUser() {
    sessionStorage.clear();
    window.location.href = "login.html";
}