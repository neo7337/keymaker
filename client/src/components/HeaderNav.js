import React, { Component } from 'react'
import { Button, Container, Divider, Grid, Header, Icon, Image, List, Menu, Responsive, Segment, Sidebar, Visibility, } from 'semantic-ui-react'
import PropTypes from 'prop-types'
import { Route, Link as RouteLink, BrowserRouter as Router } from 'react-router-dom'
import * as Scroll from 'react-scroll'
import HomepageSegment from '../components/Homepage/HomepageSegment'
import Keygen from '../views/Keygen'

var Link = Scroll.Link;
var DirectLink = Scroll.DirectLink;
var Element = Scroll.Element;
var Events = Scroll.Events;
var scroll = Scroll.animateScroll;
var scrollSpy = Scroll.scrollSpy;
var scroller = Scroll.scroller;

const getWidth = () => {
    const isSSR = typeof window === 'undefined'
    return isSSR ? Responsive.onlyTablet.minWidth : window.innerWidth
}
const HomepageHeading = ({ mobile }) => (
    <Container text>
        <Header
            as='h1'
            content='the keymaker'
            inverted
            style={{
                fontSize: mobile ? '2em' : '4em',
                fontWeight: 'normal',
                marginBottom: 0,
                marginTop: mobile ? '1.5em' : '3em',
            }}
        />
        <Header
            as='h2'
            content='Do whatever you want when you want to.'
            inverted
            style={{
                fontSize: mobile ? '1.5em' : '1.7em',
                fontWeight: 'normal',
                marginTop: mobile ? '0.5em' : '1.5em',
            }}
        />
        <Button primary size='huge'>
            Get Started
      <Icon name='right arrow' />
        </Button>
    </Container>
)

class DesktopContainer extends Component {

    constructor(props) {
        super(props);
        this.scrollToTop = this.scrollToTop.bind(this);
    }
    state = { activeItem: 'home' }
    componentDidMount() {
        Events.scrollEvent.register('begin', function () {
            console.log("begin", arguments);
        });
        Events.scrollEvent.register('end', function () {
            console.log("end", arguments);
        });
        scrollSpy.update();
    }
    componentWillUnmount() {
        Events.scrollEvent.remove('begin');
        Events.scrollEvent.remove('end');
    }

    hideFixedMenu = () => this.setState({ fixed: false })
    showFixedMenu = () => this.setState({ fixed: true })

    scrollToTop = () => {
        this.setState({ activeItem: 'home' })
        scroll.scrollToTop();
    };
    handleItemClick = (e, { name }) => {
        this.setState({ activeItem: name })
    }
    render() {
        const { children } = this.props
        const { fixed } = this.state
        const { activeItem } = this.state

        return (
            <Responsive getWidth={getWidth} minWidth={Responsive.onlyTablet.minWidth}>
                <Visibility
                    once={false}
                    onBottomPassed={this.showFixedMenu}
                    onBottomPassedReverse={this.hideFixedMenu}>
                    <Segment
                        inverted
                        textAlign='center'
                        style={{ minHeight: 700, padding: '1em 0em' }}
                        vertical>
                        <Menu
                            fixed={fixed ? 'top' : null}
                            inverted={!fixed}
                            pointing={!fixed}
                            secondary={!fixed}
                            size='large'>
                            <Container>
                                <Menu.Item as='a' name='home' active={activeItem === 'home'} onClick={this.scrollToTop}>Home</Menu.Item>
                                <Link to="products" smooth={true} duration={500} delay={100}><Menu.Item as='a' name='products' active={activeItem === 'products'} onClick={this.handleItemClick}>Products</Menu.Item></Link>
                                <Menu.Item as='a'>Contact Us</Menu.Item>
                            </Container>
                        </Menu>
                        <HomepageHeading />
                    </Segment>
                </Visibility>
                {children}
            </Responsive>
        )
    }
}

DesktopContainer.propTypes = {
    children: PropTypes.node,
}

class MobileContainer extends Component {
    state = {}
    handleSidebarHide = () => this.setState({ sidebarOpened: false })
    handleToggle = () => this.setState({ sidebarOpened: true })
    render() {
        const { children } = this.props
        const { sidebarOpened } = this.state
        return (
            <Responsive
                as={Sidebar.Pushable}
                getWidth={getWidth}
                maxWidth={Responsive.onlyMobile.maxWidth}>
                <Sidebar
                    as={Menu}
                    animation='push'
                    inverted
                    onHide={this.handleSidebarHide}
                    vertical
                    visible={sidebarOpened}>
                    <Menu.Item as='a' active>Home</Menu.Item>
                    <Menu.Item as='a'>Products</Menu.Item>
                    <Menu.Item as='a'>Contact Us</Menu.Item>
                </Sidebar>

                <Sidebar.Pusher dimmed={sidebarOpened}>
                    <Segment
                        inverted
                        textAlign='center'
                        style={{ minHeight: 350, padding: '1em 0em' }}
                        vertical>
                        <HomepageHeading mobile />
                    </Segment>
                    {children}
                </Sidebar.Pusher>
            </Responsive>
        )
    }
}

MobileContainer.propTypes = {
    children: PropTypes.node,
}

const ResponsiveContainer = ({ children }) => (
    <div>
        <DesktopContainer>{children}</DesktopContainer>
        <MobileContainer>{children}</MobileContainer>
    </div>
)

ResponsiveContainer.propTypes = {
    children: PropTypes.node,
}

export default class HeaderNav extends React.Component {
    render() {
        return (
            <ResponsiveContainer>
                {/* <HomepageSegment></HomepageSegment> */}
                <Route path="/" component={HomepageSegment}></Route>
                <Route path="keygen" component={Keygen}></Route>
            </ResponsiveContainer>
        )
    }
}