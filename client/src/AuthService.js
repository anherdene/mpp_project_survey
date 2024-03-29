import decode from 'jwt-decode';
class AuthService {
    // Initializing important variables
    constructor(domain) {
        this.domain = domain || process.env.REACT_APP_WS_URL; // API server domain
        this.fetch = this.fetch.bind(this); // React binding stuff
        this.login = this.login.bind(this);
        this.getProfile = this.getProfile.bind(this)
    }

    login(username, password) {
        // Get a token from api server using the fetch api
        localStorage.clear();
        return this.fetch(`${this.domain}/api/auth/signin`, {
            method: 'POST',
            body: JSON.stringify({
                username,
                password
            })
        }).then(res => {
            this.setToken(res.accessToken); // Setting the token in localStorage
            return Promise.resolve(res);
        })
    }

    loggedIn() {
        // Checks if there is a saved token and it's still valid
        const token = this.getToken(); // GEtting token from localstorage
        return !!token && !this.isTokenExpired(token) // handwaiving here
    }

    isTokenExpired(token) {
        try {
            const decoded = decode(token);
            if (decoded.exp < Date.now() / 1000) { // Checking if token is expired. N
                return true;
            }
            else
                return false;
        }
        catch (err) {
            return false;
        }
    }

    setToken(accessToken) {
        // Saves user token to localStorage
        localStorage.setItem('accessToken', accessToken)
    }

    getToken() {
        // Retrieves the user token from localStorage
        return localStorage.getItem('accessToken')
    }

    logout() {
        // Clear user token and profile data from localStorage
        localStorage.removeItem('accessToken');
    }

    getProfile() {
        // Using jwt-decode npm package to decode the token
        return decode(this.getToken());
    }

    getUserProfile() {
        return this.fetch(`${this.domain}/api/user/me`, {
            headers: {
                'Authorization': 'Bearer ' + localStorage.accessToken
            }
        }).then(res => {
            localStorage.setItem("username", res.username);
            let userRole = "admin";
            if (res.roles.length === 1) {
                userRole = "user";
            }
            localStorage.setItem("role", userRole);
            return res.data
        });
    }


    fetch(url, options) {
        // performs api calls sending the required authentication headers
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        };

        // Setting Authorization header
        // Authorization: Bearer xxxxxxx.xxxxxxxx.xxxxxx
        if (this.loggedIn()) {
            headers['Authorization'] = 'Bearer ' + this.getToken()
        }

        return fetch(url, {
            headers,
            ...options
        })
            .then(this._checkStatus)
            .then(response => response.json())
    }

    _checkStatus(response) {
        // raises an error in case response status is not a success
        if (response.status >= 200 && response.status < 300) { // Success status lies between 200 to 300
            return response
        } else {
            var error = new Error(response.statusText)
            error.response = response
            throw error
        }
    }
}

const jwtAuth = new AuthService();

export default jwtAuth;