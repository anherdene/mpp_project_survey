import React, {Component} from 'react';
import axios from "axios";

class Questions extends Component {
    constructor (props) {
        super(props);

        this.state = {
            questions: null
        };
    }

    async componentDidMount() {
        const surveys = (await axios.get('http://localhost:8081/')).data;
        this.setState({
            surveys,
        });
    }

    handleClick () {
        console.log("clicked!")
    }

    render () {
        return (
            <di>
                <table className="table table-striped" id="newListTable">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>Title</th>
                        <th>Question type</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <th>1.</th>
                            <th>How is the food? <span className="question-required"> *</span></th>
                            <th>Multiple choice</th>
                            <th>
                                <button className="btn btn-sm btn-edit" onClick={this.handleClick()}><i className="fas fa-edit"></i></button>
                                <button className="btn btn-sm btn-delete" onClick={this.handleClick()}><i className="fas fa-trash"></i></button>
                            </th>
                        </tr>
                        <tr>
                            <th>2.</th>
                            <th>Describe campus experience? <span className="question-required"> *</span></th>
                            <th>Open ended</th>
                            <th>
                                <button className="btn btn-sm btn-edit" onClick={this.handleClick()}><i className="fas fa-edit"></i></button>
                                <button className="btn btn-sm btn-delete" onClick={this.handleClick()}><i className="fas fa-trash"></i></button>
                            </th>
                        </tr>
                    </tbody>
                </table>
            </di>
        )
    }
}

export default Questions