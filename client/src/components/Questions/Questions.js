import React, {Component} from 'react';
import axios from "axios";
import QuestionRating from "./QuestionRating";

class Questions extends Component {
    constructor (props) {
        super(props);
        console.log(props);
        this.state = {
            survey: props.children
        };
    }

    async componentDidMount() {
        const surveys = (await axios.get('http://localhost:8081/')).data;
        this.setState({
            surveys,
        });
    }

    submitSurvey () {
        this.props.callbackFromParent("asd");
    }

    render () {
        const {survey} = this.state;
        console.log(this.state);
        return (
            <div className="container">
                {/*<QuestionRating/>*/}
                {
                    survey.questions.map((question, idx) =>(
                        <div>
                            <QuestionRating/>
                            <div className="lead" key={1}>{idx+1}. {question.content} <span className="question-required">{question.optional ? '' : '*'}</span></div>
                            {
                                question.type === 'MC' ? (
                                    <div className="form-group answer-group">
                                        {question.choices.map(choice => (
                                        <div>
                                            <div className="custom-control custom-radio">
                                                <input type="radio" className="custom-control-input" id={choice.id}
                                                       name={question.id}/>
                                                <label className="custom-control-label"
                                                       htmlFor={choice.id}>{choice.content}</label>
                                            </div>
                                        </div>
                                        ))
                                        }
                                </div>
                                ) : (
                                    <div className="form-group answer-group">
                                    <textarea className="form-control" rows="5" id="comment" />
                                </div>
                                )
                            }
                        </div>
                    ))
                }
                <button type="button" className="btn btn-dark" onClick={this.submitSurvey()}>Submit</button>
            </div>
        )
    }
}

export default Questions