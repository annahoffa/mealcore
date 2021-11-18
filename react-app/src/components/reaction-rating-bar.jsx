import React, { useEffect } from 'react';
import PropTypes from 'prop-types';
import Rating from '@mui/material/Rating';
import {
  MoodBad as MoodBadIcon,
  SentimentVeryDissatisfied as SentimentVeryDissatisfiedIcon,
  SentimentDissatisfied as SentimentDissatisfiedIcon,
  SentimentSatisfiedAlt as SentimentSatisfiedAltIcon,
  SentimentVerySatisfied as SentimentVerySatisfiedIcon,
} from '@mui/icons-material';
import apiCall from '../utils/apiCall';


const customIcons = {
  1: {
    icon: <MoodBadIcon />,
    label: 'Bardzo źle',
  },
  2: {
    icon: <SentimentVeryDissatisfiedIcon />,
    label: 'Słabo',
  },
  3: {
    icon: <SentimentDissatisfiedIcon />,
    label: 'Średnio',
  },
  4: {
    icon: <SentimentSatisfiedAltIcon />,
    label: 'Dobrze',
  },
  5: {
    icon: <SentimentVerySatisfiedIcon />,
    label: 'Świetnie',
  },
};

const IconContainer = (props) => {
  const { value, ...other } = props;
  return <span {...other}>{customIcons[value].icon}</span>;
};

IconContainer.propTypes = {
  value: PropTypes.number.isRequired,
};

const ReactionRatingBar = ({ category, savedReaction }) => {
  const [value, setValue] = React.useState(savedReaction);
  console.log('saved reaction');
  console.log(savedReaction);
  useEffect(() => (sendReaction(category, value)), [value]);

  const sendReaction = (category, value) => {
    //TODO: change POST to PUT for all reactions (new & modified)
    apiCall(`/api/user/addReaction?category=${category}&value=${value}`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
    })
    .catch(error => console.log(error));
  };

  return (
    <Rating
      value={value}
      onChange={(event, newValue) => {
        setValue(newValue);
      }}
      IconContainerComponent={IconContainer}
      highlightSelectedOnly
    />);
};

export default ReactionRatingBar;