import { createBrowserHistory } from 'history';


const UNAUTHORIZED = 401;

const apiCall = async(url, params) => {

  const request = !params ? {} : params;
  if(!request.headers) request.headers = {};
  request.credentials = 'include';

  let response = await fetch(process.env.REACT_APP_API_URL + url, request);
  if(response.status === UNAUTHORIZED) {
    createBrowserHistory().push('/logout');
  }
  if(!response.ok) throw new Error(response.statusText);
  //return response;
  if(!response.body) return null;
  return await response.json();
};

export default apiCall;
