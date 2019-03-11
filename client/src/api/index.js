import axios from 'axios';
// import { hashHistory } from 'react-router';
// import decode from 'jwt-decode';

const fetcher = axios.create({
    baseURL: process.env.REACT_APP_WS_URL,
    headers: {
        'Content-Type': 'application/json',
        // 'Authorization': localStorage.session
    }
});

export const fetchSurvey = (surveyId) => {
    return fetcher.get(`/api/surveys/get/${surveyId}`)
        .then(res => {
            return res.data;
        })
        .catch(function(error) {
            console.log("error " + error.message)
        });
};

export const fetchSurveys = (user) => {
    return fetcher.get(`/api/surveys/index`).then(res => res.data);
};

export const uploadSurvey = (file) => {
    return fetcher.post("/admin/uploadFile", file, {
        headers: {
            'Content-Type': 'multipart/form-data',
            'Authorization': 'Bearer ' + localStorage.accessToken
        }
    }).then(res => res.data);
};

export const createUser = (username, password) => {
    return fetcher.post("/api/auth/signup", {
        username: username,
        password: password
    }).then(res => res.data);
};

export const submitSurveyAnswer = (answers) => {
    console.log(answers);
    // return null;
    return fetcher.post("/api/surveys/submitAnswer", {
        answers
    },{
        headers: {
            'Authorization': 'Bearer ' + localStorage.accessToken
        }
    }).then(res => res.data);
};

export const submitQuestionRating = (questionId, rating) => {
    return fetcher.get("/api/question/rate?questionId=" + questionId + "&rate=" + rating, {
        headers: {
            'Authorization': 'Bearer ' + localStorage.accessToken
        }
    }).then(res => res.data);
};

// -----------------------------------------

export const logout = () => {
    delete localStorage.session;
    return Promise.resolve();
};

export const fetchCurrentUser = () => {
    return fetcher.get("/users/me").then(res => {
        return res.data;
    });
};

export const fetchUserSurveys = (user) => {
    return fetcher.get(`/users/${user.id}/surveys`).then(res => res.data);
};

export const fetchResults = (surveyId) => {
    return fetcher.get(`/surveys/${surveyId}/results`).then(res => res.data);
};

export const createSurvey = (userId, initSurvey) => {
    return fetcher.post(`/users/${userId}/surveys`, initSurvey).then(res => res.data);
};

export const saveResult = (surveyId, result) => {
    return fetcher.post(`/surveys/${surveyId}/results`, result);
};

export const deleteSurvey = surveyId => {};

export const updateSurvey = (survey) => {
    return fetcher.put(`/surveys/${survey.id}`, survey).then(res => res.data);
};

export const deleteResults = (surveyId, results) => {
    return Promise.all(results.map(result => fetcher.delete(`/surveys/${surveyId}/results/${result.id}`)));
};
