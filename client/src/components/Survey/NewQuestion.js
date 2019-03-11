import withRouter from "react-router/es/withRouter";
import React, {Component, Fragment} from 'react';

class NewQuestion extends Component {
    constructor (props) {
        super(props);
        this.state = {
            question: ''
        };
    }

    updateQuestion(value) {
        this.setState({
            question: value,
        });
    }

    submit() {
        this.props.newQuestion(this.state.question);

        this.setState({
            question: '',
        });
    }
}

export default withRouter (NewQuestion)