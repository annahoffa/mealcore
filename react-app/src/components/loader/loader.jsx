import { keyframes } from '@emotion/css';
import styled from '@emotion/styled';
import React from 'react';


const spinner = keyframes`
  0% {
    transform: rotate(0deg);
  }
  50% {
    transform: rotate(180deg);
    opacity: 0.5;
  }
  100% {
    transform: rotate(360deg);
  }
`;

const LoaderStyle = styled('span')`
  display: inline-flex;
  width: ${(props) => (props.width ? props.width : '14px')};
  height: ${(props) => (props.height ? props.height : '14px')};
  border-radius: 50%;
  overflow: hidden;
  border-width: 2px;
  border-style: solid;
  border-color: ${(props) => props.loaderColor};
  border-top-color: transparent !important;
  animation: ${spinner} 1s linear infinite;
`;

const Loader = ({ loaderColor = '', ...props }) => (
  <LoaderStyle loaderColor={loaderColor} {...props} />
);

export default Loader;