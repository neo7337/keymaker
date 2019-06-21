import React, { Component } from 'react';
import { Button, Container, Divider, Grid, Header, Icon, Image, List, Menu, Responsive, Segment, Sidebar, Visibility, } from 'semantic-ui-react'
import PropTypes from 'prop-types'
import * as Scroll from 'react-scroll'

var Link = Scroll.Link;
var DirectLink = Scroll.DirectLink;
var Element = Scroll.Element;
var Events = Scroll.Events;
var scroll = Scroll.animateScroll;
var scrollSpy = Scroll.scrollSpy;

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
    state = {}

    hideFixedMenu = () => this.setState({ fixed: false })
    showFixedMenu = () => this.setState({ fixed: true })

    scrollToTop = () => {
        scroll.scrollToTop();
    };
    render() {
        const { children } = this.props
        const { fixed } = this.state

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
                                <Menu.Item as='a' active onClick={this.scrollToTop}>Home</Menu.Item>
                                <Link to="products"><Menu.Item as='a'>Products</Menu.Item></Link>
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

export default class Homepage extends React.Component {

    render() {
        return (
            <div>
                <ResponsiveContainer>
                    <Segment style={{ padding: '8em 0em' }} vertical name="products">
                        <Grid container stackable verticalAlign='middle'>
                            <Grid.Row>
                                <Grid.Column width={8}>
                                    <Header as='h3' style={{ fontSize: '2em' }}>
                                        We Help Companies and Companions</Header>
                                    <p style={{ fontSize: '1.33em' }}>
                                        We can give your company superpowers to do things that they never thought possible.
                                        Let us delight your customers and empower your needs... through pure data analytics.</p>
                                </Grid.Column>
                                <Grid.Column floated='right' width={6}>
                                    <Header as='h3' style={{ fontSize: '2em' }}>
                                        We Make Bananas That Can Dance</Header>
                                    <p style={{ fontSize: '1.33em' }}>
                                        Yes that's right, you thought it was the stuff of dreams, but even bananas can be
                                        bioengineered.</p>
                                </Grid.Column>
                            </Grid.Row>
                            <Grid.Row>
                                <Grid.Column textAlign='center'>
                                    <Button size='huge'>Check Them Out</Button>
                                </Grid.Column>
                            </Grid.Row>
                        </Grid>
                    </Segment>
                    <Segment style={{ padding: '0em' }} vertical>
                        <Grid celled='internally' columns='equal' stackable>
                            <Grid.Row textAlign='center'>
                                <Grid.Column style={{ paddingBottom: '5em', paddingTop: '5em' }}>
                                    <Header as='h3' style={{ fontSize: '2em' }}>
                                        "What a Company"</Header>
                                    <p style={{ fontSize: '1.33em' }}>That is what they all say about us</p>
                                </Grid.Column>
                                <Grid.Column style={{ paddingBottom: '5em', paddingTop: '5em' }}>
                                    <Header as='h3' style={{ fontSize: '2em' }}>
                                        "I shouldn't have gone with their competitor."</Header>
                                    <p style={{ fontSize: '1.33em' }}>
                                        <Image avatar src='/images/avatar/large/nan.jpg' />
                                        <b>Nan</b> Chief Fun Officer Acme Toys</p>
                                </Grid.Column>
                            </Grid.Row>
                        </Grid>
                    </Segment>
                    <Segment style={{ padding: '8em 0em' }} vertical>
                        <Container text>
                            <Header as='h3' style={{ fontSize: '2em' }}>
                                Breaking The Grid, Grabs Your Attention</Header>
                            <p style={{ fontSize: '1.33em' }}>
                                Instead of focusing on content creation and hard work, we have learned how to master the
                                art of doing nothing by providing massive amounts of whitespace and generic content that
                                can seem massive, monolithic and worth your attention.</p>
                            <Button as='a' size='large'>
                                Read More</Button>
                            <Divider
                                as='h4'
                                className='header'
                                horizontal
                                style={{ margin: '3em 0em', textTransform: 'uppercase' }}>
                                <a href='#'>Case Studies</a>
                            </Divider>
                            <Header as='h3' style={{ fontSize: '2em' }}>
                                Did We Tell You About Our Bananas?</Header>
                            <p style={{ fontSize: '1.33em' }}>
                                Yes I know you probably disregarded the earlier boasts as non-sequitur filler content, but
                                it's really true. It took years of gene splicing and combinatory DNA research, but our
                                bananas can really dance.</p>
                            <Button as='a' size='large'>
                                I'm Still Quite Interested</Button>
                        </Container>
                    </Segment>
                </ResponsiveContainer>
            </div>
        );
    }
}