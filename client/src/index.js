import React from 'react';
import ReactDOM from 'react-dom';
import { Route, Link, BrowserRouter as Router } from 'react-router-dom'
import Homepage from './views/Homepage'
import Keygen from './views/Keygen'
import Footer from './components/Footer'
import HeaderNav from './components/HeaderNav'

const routing = (
    <Router>
        <div>
            <HeaderNav></HeaderNav>
            <Route path="/keygen" component={Keygen} />
            <Footer></Footer>
        </div>
    </Router>
)

ReactDOM.render(routing, document.getElementById('root'))
// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
//serviceWorker.unregister();