import React from 'react';
import StarRatingComponent from 'react-star-rating-component';

class QuestionRating extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            rating: 3
        };
    }

    onStarClick(nextValue, prevValue, name) {
        this.setState({rating: nextValue});
    }

    render() {
        const { rating } = this.state;

        return (
            <div className="question-rating">
                <StarRatingComponent
                    name="rate1"
                    starCount={5}
                    value={rating}
                    onStarClick={this.onStarClick.bind(this)}
                />
            </div>
        );
    }
}

export default QuestionRating;