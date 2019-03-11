import React, {Component} from 'react';
import axios from "axios";
import {fetchSurveyQuestionResults} from "../../api";

class AdminQuestionResults extends Component {
    constructor (props) {
        super(props);
        this.state = {
            survey: this.props.children,
            results: null
        };
    }

    async componentDidMount () {
        await this.getSurveyResults();
    }

    async getSurveyResults () {
        const results = (await fetchSurveyQuestionResults(this.state.id));
        this.setState({
            results: results
        })
    }

    render () {
        const {survey} = this.state;

        if (survey === null) return <p>Loading ...</p>;
        return (
            <div>
                <ul>
                    {survey.questions.map((question, idx) =>(
                        <li>
                            <header>
                                <div className="question-number">{idx+1}.</div>
                                <h3>{question.content}</h3>
                                <h4>Multiple Choice</h4>
                                <br/>
                                <AdminResultDetails question={question} survey={survey}/>
                                {/*<div style={chartStyle}>*/}
                                    {/*<DoughnutChart data={data} options={chartOptions} width="600" height="250"/>*/}

                                {/*</div>*/}
                            </header>
                        </li>
                    ))}
                </ul>
            </div>
        )
    }
}

class AdminResultDetails extends React.Component{

    constructor (props) {
        super(props);
        console.log(props);
        this.state = {
            question: this.props.question,
            results: null,
            survey: this.props.survey
        };
    }

    async componentDidMount() {
        const results = (await fetchSurveyQuestionResults(this.state.survey.id));
        this.setState({
            results: results
        });
    }

    render() {
        let DoughnutChart = require("react-chartjs").Doughnut;

        let data = [
            {
                value: 300,
                color:"#F7464A",
                highlight: "#FF5A5E",
                label: "Red"
            },
            {
                value: 50,
                color: "#46BFBD",
                highlight: "#5AD3D1",
                label: "Green"
            },
            {
                value: 100,
                color: "#FDB45C",
                highlight: "#FFC870",
                label: "Yellow"
            }
        ];

        let chartOptions = {
            maintainAspectRatio: false
        };


        const {chartStyle} = {
            float: "left"
        };
        const {resultStyle} = {
            float: "right"
        };
        return (
            <div>
                <div className="row">
                <div className="col-md-8" style={chartStyle}>
                    <DoughnutChart data={data} options={chartOptions} width="400" height="250"/>
                </div>
                <div className="col-md-4" style={resultStyle}>
                    <h3>Results: </h3>
                    <ul>
                        {
                            this.state.question.choices.map((choice, idx) =>(
                                <li>
                                    <div>
                                        {idx+1}. {choice.id}
                                    </div>
                                </li>
                            ))
                        }
                    </ul>
                </div>
                </div>
            </div>
        )
    }

}

export default AdminQuestionResults;