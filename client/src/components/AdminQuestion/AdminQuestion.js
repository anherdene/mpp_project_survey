import React, {Component} from 'react';
import axios from 'axios';
import auth0Client from '../../Auth';
import AdminSideNav from "../AdminSideNav/AdminSideNav";
import Questions from "../Questions/Questions";
import NavBar from "../NavBar/NavBar";

class AdminQuestion extends Component {
    constructor(props) {
        super(props);
        console.log(props);
        this.state = {
            survey: null
        };

        this.submitAnswer = this.submitAnswer.bind(this);
        this.newQuestion = this.newQuestion.bind (this);
    }

    async componentDidMount() {
        await this.refreshQuestion();
    }

    async refreshQuestion() {
        const { match: { params } } = this.props;
        const survey = (await axios.get(`http://localhost:8081/${params.surveyId}`)).data;
        this.setState({
            survey,
        });
    }

    async submitAnswer(answer) {
        await axios.post(`http://localhost:8081/answer/${this.state.survey.id}`, {
            answer,
        }, {
            headers: { 'Authorization': `Bearer ${auth0Client.getIdToken()}` }
        });
        await this.refreshQuestion();
    }

    async newQuestion(question) {
        await axios.post(`http://localhost:8081/question/${this.state.survey.id}`, {
            question,
        }, {
            headers: { 'Authorization': `Bearer ${auth0Client.getIdToken()}` }
        });
        await this.refreshQuestion();
    }

    render() {
        const {survey} = this.state;
        if (survey === null) return <p>Loading ...</p>;
        return (
            <div>
                <NavBar />
            <div className="container">
                <div className="row">
                    <AdminSideNav/>
                    <div className="jumbotron col-9">
                        <h4 className="survey-title">{survey.title}</h4>
                        <p className="lead">{survey.description}</p>
                        <hr className="my-4" />
                        <Questions/>
                    </div>
                </div>
            </div>
            </div>
        )
    }
}

export default AdminQuestion;