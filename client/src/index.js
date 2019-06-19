import React from 'react';
import ReactDOM from 'react-dom';
import { Menu, Segment } from 'semantic-ui-react'
import { Route, Link, BrowserRouter as Router } from 'react-router-dom'
import './index.css';
import * as serviceWorker from './serviceWorker';
import Users from './users'
import Homepage from './views/Homepage'
import Keygen from './views/Keygen'
import Footer from './components/Footer'

const routing = (
    <Router>
        <div>
            <Menu color={"black"}>
                <Link to="/">
                    <Menu.Item>
                        <i>Keymaker</i>
                    </Menu.Item>
                </Link>
                <Link to="/users">
                    <Menu.Item
                        name='Users' />
                </Link>
                <Menu.Menu position='right'>
                    <Menu.Item
                        name='logout'
                    />
                </Menu.Menu>
            </Menu>
            <Route exact path="/" component={Homepage} />
            <Route path="/users" component={Users} />
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
