import React from 'react';
import { Switch, Route } from 'react-router-dom';
import { CssBaseline } from '@material-ui/core';
import Header from './components/header/header.component';
import BMICalculatorPage from './pages/BMI-calculator/BMI-calculator.component';
import DashboardPage from './pages/dashboard/dashboard.component';
import ProblematicProductsSummaryPage from './pages/problematic-products-summary/problematic-products-summary';
import Homepage from './pages/homepage/homepage.component';
import ProductSearchPage from './pages/product-search/product-search.component';
import SearchResultsPage from './pages/search-results/search-results.component';
import ProductInfoPage from './pages/product-info/product-info.component';
import SuggestNewProductPage from './pages/suggest-new-product/suggest-new-product';
import ReviewProductsSuggestions from './pages/review-products-suggestions/review-products-suggestions';
import MyAccountPage from './pages/my-account/my-account.component';
import SignUpPage from './pages/sign-up/sign-up-page.component';
import LogInPage from './pages/log-in/log-in-page.component';
import LogOutPage from './pages/log-out/logOutPage';
import Footer from './components/footer/footer.component';
import PrivacyPolicyPage from './pages/footer-links/privacy-policy';
import TermsOfServicePage from './pages/footer-links/terms-of-service';

import './App.scss';


// const useStyles = makeStyles((theme) => ({
//   appBar: {
//     transition: theme.transitions.create(['margin', 'width'], {
//       easing: theme.transitions.easing.sharp,
//       duration: theme.transitions.duration.leavingScreen,
//     }),
//   },
// }));

const App = () => {

  //const [cookies, setCookies] = useCookies(['JSESSIONID']);
  //if (cookies.JSESSIONID) {logIn()} else logOut();

  return (
    <div className='App'>
      <CssBaseline />
      <Header />
      <Switch>
        <Route exact path='/' component={Homepage} />
        <Route path='/bmicalculator' component={BMICalculatorPage} />
        <Route exact path='/productsearch' component={ProductSearchPage} />
        <Route path='/searchresults/:query' component={SearchResultsPage} />
        <Route path='/productinfo/:id' component={ProductInfoPage} />
        <Route path='/privacypolicy' component={PrivacyPolicyPage} />
        <Route path='/terms' component={TermsOfServicePage} />

        {/* Authenticated-only pages: */}
        <Route path='/dashboard' component={DashboardPage} />
        <Route path='/summary' component={ProblematicProductsSummaryPage} />
        <Route path='/suggestproduct' component={SuggestNewProductPage} />
        <Route exact path='/myaccount' component={MyAccountPage} />
        <Route exact path='/logout' component={LogOutPage} />

        {/* Admin-only pages: */}
        <Route exact path='/review' component={ReviewProductsSuggestions} />

        {/* Unauthenticated-only pages: */}
        <Route path='/login' component={LogInPage} />
        <Route exact path='/signup' component={SignUpPage} />
      </Switch>
      <Footer />
    </div>
  );
};

export default App;
