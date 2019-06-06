import React from "react";


export default class Footer extends React.Component {
  render() {
    return (
      <div>
        <div id="footer">
          <div class="col-md-12" style={{ textAlign: 'center', padding: '10px' }}>
            <ul class="social-network social-circle">
              <li><a href="https://github.com/neo7337" class="icoGithub" title="Github"><i class="fa fa-github"></i></a></li>
              <li><a href="https://stackoverflow.com/users/5894292/neo73" class="icoStackoverflow" title="Stackoverflow"><i
                class="fa fa-stack-overflow"></i></a>
              </li>
              <li><a href="https://twitter.com/adityak007mr" class="icoTwitter" title="Twitter"><i
                class="fa fa-twitter"></i></a></li>
              <li><a href="https://mail.google.com/mail/?view=cm&fs=1&to=adityak007mr@gmail.com&su=SUBJECT&body=BODY"
                class="icoEmail" title="Google +"><i class="fa fa-envelope"></i></a></li>
              <li><a href="https://www.linkedin.com/in/adi-kumar/" class="icoLinkedin" title="Linkedin"><i
                class="fa fa-linkedin"></i></a></li>
            </ul>
          </div>
        </div>
      </div>
    );
  }
}
