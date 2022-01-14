import React from 'react';
import MainContent from '../../components/main-content/main-content.component';
import BMIForm from '../../components/BMI-form/BMI-form.component';
import BMIResults from '../../components/BMI-results/BMI-results.component';

import './BMI-calculator.styles.scss';


class BMICalculatorPage extends React.Component {
  constructor() {
    super();

    this.state = {
      weight: '',
      height: '',
      showResults: false,
    };
  }

  handleChange = event => {
    const { name, value } = event.target;
    this.setState({ [name]: value });
  };

  handleSubmit = event => {
    console.log(this.state);
    this.setState({ showResults: true });
    event.preventDefault();
  };

  // Necessary for reloading the page when clicking the link from header
  // (because the url doesn't change, the page won't reload without componentDidUpdate)
  // Not very efficient, but works
  componentDidUpdate(prevProps) {
    if(prevProps.location.key !== this.props.location.key) {
      this.setState({
        weight: '',
        height: '',
        showResults: false,
      });
    }
  }

  render() {
    return (
      <MainContent>
        {this.state.showResults ?
          (<BMIResults
            userWeight={this.state.weight}
            userHeight={this.state.height} />)
          : (<BMIForm
            handleChange={this.handleChange}
            handleSubmit={this.handleSubmit}
            state={this.state} />)
        }
      </MainContent>
    );
  }
}

export default BMICalculatorPage;
