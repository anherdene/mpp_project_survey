import React, {Component} from 'react';
import QuestionRating from "./QuestionRating";
import {submitQuestionRating, submitSurveyAnswer} from "../../api";

class Questions extends Component {
    constructor (props) {
        super(props);
        let formValues = [];
        props.children.questions.map(question => {
            let curQ = {};
            curQ.surveyId = props.children.id;
            curQ.questionId = question.id;
            curQ.choiceId = null;
            curQ.textAnswer = '';
            curQ.type = question.type;
            formValues.push(curQ);

            return formValues;
        });
        this.state = {
            survey: props.children,
            formValues: formValues
        };

    }

    changeHandler = event => {

        const name = event.target.name;
        const value = event.target.value;

        this.setState(prevState => ({
            formValues: prevState.formValues.map(
            obj => {
                if (obj.type === 'MC') {
                    return (obj.questionId === parseInt(name) ? Object.assign(obj, { choiceId: parseInt(value) }) : obj)
                } else if (obj.type === 'OE') {
                    return (obj.questionId === parseInt(name) ? Object.assign(obj, { textAnswer: value.toString() }) : obj)
                }

                return null;
            }
            )
        }));

    };

    ratingCallback = (qId, rating) => {
        submitQuestionRating(qId, rating);
    };

    submitSurvey () {
        console.log(this.state.formValues);
        this.props.callbackFromParent(this.state.formValues);
    }

    render () {
        const {survey} = this.state;
        return (
            <div>
                {
                    survey.questions.map((question, idx) =>(
                        <div key={idx}>
                            <QuestionRating callbackFromParent={this.ratingCallback} children={question}/>
                            <div className="lead" key={1}>{idx+1}. {question.content} <span className="question-required">{question.optional ? '' : '*'}</span></div>
                            {
                                question.type === 'MC' ? (
                                    <div className="form-group answer-group">
                                        {question.choices.map(choice => (
                                        <div key={choice.id}>
                                            <div className="custom-control custom-radio">
                                                <input type="radio" className="custom-control-input" id={choice.id} defaultChecked={choice.default}
                                                       name={question.id} value={choice.id} onChange={this.changeHandler} />
                                                <label className="custom-control-label"
                                                       htmlFor={choice.id}>{choice.content}</label>
                                            </div>
                                        </div>
                                        ))
                                        }
                                </div>
                                ) : (
                                    <div className="form-group answer-group">
                                    <textarea className="form-control" rows="5" name={question.id} id={question.id} onChange={this.changeHandler}/>
                                </div>
                                )
                            }
                        </div>
                    ))
                }
                <button type="button" className="btn btn-dark" onClick={() => {this.submitSurvey()}}>Submit</button>
            </div>
        )
    }
}

export default Questions