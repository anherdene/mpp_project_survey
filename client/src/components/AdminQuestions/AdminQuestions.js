import React, {Component} from 'react';
import axios from "axios";
import EditQuestionModal from "./EditQuestionModal";
import auth0Client from "../../Auth";

class Questions extends Component {
    constructor (props) {
        super(props);
        console.log(props);
        this.state = {
            survey: props.children,
            isModalOpen: false,
            isInnerModalOpen: false,
            question: null
        };
        this.openModal = this.openModal.bind(this);
        this.closeModal = this.closeModal.bind(this);
}

    async componentDidMount() {
        const surveys = (await axios.get('http://localhost:8081/')).data;
        this.setState({
            surveys,
        });
    }

    handleClick (question) {
        console.log(question);
    }

    closeModal() {
        this.setState({
            isModalOpen: false
        });
    }

    openModal(question) {
        this.setState({
            isModalOpen: true,
            question: question
        });
    }

    async editQuestion(question) {
        this.setState({
            disabled: true,
        });

        await axios.post('http://localhost:8081', {
            title: this.state.title,
            description: this.state.description,
        }, {
            headers: { 'Authorization': `Bearer ${auth0Client.getIdToken()}` }
        }).then(response=> {
            this.props.history.push('/survey/' + response.data.id);
            console.log(response.data);
        });

    }


    render () {
        const {survey} = this.state;
        const qTitle = {
            width: '60%'
        };
        const mCloseButton = {
            float: 'right'
        };
        // overwrite style
        const modalStyle = {
            overlay: {
                backgroundColor: "rgba(0, 0, 0,0.5)"
            }
        };

        // const mainStyle = {
        //     app: {
        //         margin: "120px 0"
        //     },
        //     button: {
        //         backgroundColor: "#408cec",
        //         border: 0,
        //         float: "right",
        //         padding: "12px 20px",
        //         color: "#fff",
        //         margin: "0 auto",
        //         width: 150,
        //         display: "block",
        //         borderRadius: 3
        //     }
        // };
        return (
            <div>
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
                    {
                        survey.questions.map((question, idx) =>(
                            <tr key={idx}>
                                <td>{idx + 1}.</td>
                                <td style={qTitle}>{question.content} <span className="question-required"> *</span></td>
                                <td>{question.type === 'MC' ? 'Multiple Choice' : 'Open ended'}</td>
                                <td>
                                    <button className="btn btn-sm btn-edit" onClick={() => {this.openModal(question)}}><i className="fas fa-edit"></i></button>
                                    <button className="btn btn-sm btn-delete" onClick={this.openModal}><i className="fas fa-trash"></i></button>
                                </td>
                            </tr>
                        ))
                    }
                    <EditQuestionModal isModalOpen={this.state.isModalOpen}
                        closeModal={this.closeModal}
                        style={modalStyle}>

                            <div className="card-body text-left">
                                <div className="form-group">
                                    <label htmlFor="exampleInputEmail1">Survey:</label>
                                    <input
                                        disabled={this.state.disabled}
                                        type="text"
                                        onBlur={(e) => {
                                            this.updateTitle(e.target.value)
                                        }}
                                        className="form-control"
                                        placeholder="Give your survey a title."
                                    />
                                </div>
                                <div className="form-group">
                                    <label htmlFor="exampleInputEmail1">Description:</label>
                                    <input
                                        disabled={this.state.disabled}
                                        type="text"
                                        onBlur={(e) => {
                                            this.updateDescription(e.target.value)
                                        }}
                                        className="form-control"
                                        placeholder="Give more context to your survey."
                                    />
                                </div>
                                <button
                                    disabled={this.state.disabled}
                                    className="btn btn-success"
                                    onClick={() => {
                                        this.editQuestion(this.state.question)
                                    }}>
                                    Save
                                </button>
                                <button
                                    style={mCloseButton}
                                    className="btn btn-primary"
                                    onClick={this.closeModal}
                                >
                                    Close
                                </button>
                            </div>


                    </EditQuestionModal>
                    </tbody>
                </table>
            </div>
        )
    }
}

export default Questions