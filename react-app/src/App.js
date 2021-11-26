import React, { useState } from 'react';
import { Switch, Route } from 'react-router-dom';
import clsx from 'classnames';

import { CssBaseline, makeStyles } from '@material-ui/core';
import Header from './components/header/header.component';
import BMICalculatorPage from './pages/BMI-calculator/BMI-calculator.component';
import DashboardPage from './pages/dashboard/dashboard.component';
import ProblematicProductsSummaryPage from './pages/problematic-products-summary/problematic-products-summary';
import Homepage from './pages/homepage/homepage.component';
import ProductSearchPage from './pages/product-search/product-search.component';
import SearchResultsPage from './pages/search-results/search-results.component';
import ProductInfoPage from './pages/product-info/product-info.component';
import PricingPage from './pages/pricing/pricing.component';
import MyAccountPage from './pages/my-account/my-account.component';
import SignUpPage from './pages/sign-up/sign-up-page.component';
import LogInPage from './pages/log-in/log-in-page.component';
import LogOutPage from './pages/log-out/logOutPage';
import Footer from './components/footer/footer.component';
import PrivacyPolicyPage from './pages/footer-links/privacy-policy';
import TermsOfServicePage from './pages/footer-links/terms-of-service';

import './App.scss';


const useStyles = makeStyles((theme) => ({
  root: {
    display: 'flex',
  },
  appBar: {
    transition: theme.transitions.create(['margin', 'width'], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen,
    }),
  },
}));

const App = () => {
  const classes = useStyles();
  const [open, setOpen] = useState(false);

  //const [cookies, setCookies] = useCookies(['JSESSIONID']);
  //if (cookies.JSESSIONID) {logIn()} else logOut();

  return (
    <div className={classes.root}>
      <CssBaseline />
      <Header />

      <div className='App'>
        <Switch>
          <Route exact path='/' component={Homepage} />
          <Route path='/bmicalculator' component={BMICalculatorPage} />
          <Route exact path='/productsearch' component={ProductSearchPage} />
          <Route path='/searchresults/:query' component={SearchResultsPage} />
          <Route path='/productinfo/:id' component={ProductInfoPage} />
          <Route path='/pricing' component={PricingPage} />
          <Route path='/privacypolicy' component={PrivacyPolicyPage} />
          <Route path='/terms' component={TermsOfServicePage} />

          {/* Authenticated-only pages: */}
          <Route path='/dashboard' component={DashboardPage} />
          <Route path='/summary' component={ProblematicProductsSummaryPage} />
          <Route exact path='/myaccount' component={MyAccountPage} />
          <Route exact path='/logout' component={LogOutPage} />

          {/* Unauthenticated-only pages: */}
          <Route path='/login' component={LogInPage} />
          <Route exact path='/signup' component={SignUpPage} />
        </Switch>
        <Footer />
      </div>
    </div>
  );
};

export default App;
