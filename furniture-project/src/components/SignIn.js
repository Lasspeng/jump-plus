import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import {
  MDBContainer,
  MDBTabs,
  MDBTabsItem,
  MDBTabsLink,
  MDBTabsContent,
  MDBTabsPane,
  MDBBtn,
  MDBIcon,
  MDBInput,
  MDBCheckbox
}
from 'mdb-react-ui-kit';
import { useDispatch, useSelector } from 'react-redux';
import { changeCurrentUserId } from '../redux/user';
import Navbar from './Navbar';


function SignIn() {
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const userId = useSelector((state) => state.userId.id);
    const [justifyActive, setJustifyActive] = useState('tab1');
    const [loginEmail, setLoginEmail] = useState('');
    const [loginPassword, setLoginPassword] = useState('');
    const [regFirstName, setRegFirstName] = useState('');
    const [regLastName, setRegLastName] = useState('');
    const [regEmail, setRegEmail] = useState('');
    const [regPassword, setRegPassword] = useState('');

  const handleJustifyClick = (value) => {
    if (value === justifyActive) {
      return;
    }

    setJustifyActive(value);
  };

  function submitHandler( { setId, setUser }) {
    if (justifyActive === "tab1") {
        let userList = [];
        fetch("http://localhost:3000/users", {
            method: "GET"
        })
        .then((response) => response.json())
        .then((data) => {
            userList = data;
            for (let i = 0; i < userList.length; i++) {
                // console.log(userList[i].email, loginEmail, userList[i].password, loginPassword, i);
                if (userList[i].email === loginEmail && userList[i].password === loginPassword) {
                    dispatch(changeCurrentUserId(userList[i].id));
                    // console.log(userList[i].id);
                    navigate("/");
                    return;
                }
            }
            alert("User with these credentials could not be found. Please try again.");
        })
    }
    else if (justifyActive === "tab2") {
        if (regFirstName === "" || regLastName === "" || regEmail === "" || regPassword === "") {
            alert("Fill out all fields");
            return;
        }
        fetch("http://localhost:3000/users", {
            method: "POST",
            body: JSON.stringify({
                id: null,
                first_name: regFirstName,
                last_name: regLastName,
                email: regEmail,
                password: regPassword,
                orders: []
            }),
            headers: {
                "Content-type": "application/json; charset=UTF-8",
            },
        })
        .then((response) => response.json())
        .then((data) => console.log(data));
        alert("Account successfully created. User can now sign in");
        // eslint-disable-next-line no-unused-expressions
        window.location.reload;
    }
  }

  return (
    <div>
        <Navbar />
        <MDBContainer className="p-3 my-5 d-flex flex-column w-25" style={{"width": "25%!important"}}>

        <MDBTabs pills justify className='mb-3 d-flex flex-row justify-content-between' >
            <MDBTabsItem>
            <MDBTabsLink onClick={() => handleJustifyClick('tab1')} active={justifyActive === 'tab1'}>
                Login
            </MDBTabsLink>
            </MDBTabsItem>
            <MDBTabsItem>
            <MDBTabsLink onClick={() => handleJustifyClick('tab2')} active={justifyActive === 'tab2'}>
                Register
            </MDBTabsLink>
            </MDBTabsItem>
        </MDBTabs>

        <MDBTabsContent>

            <MDBTabsPane show={justifyActive === 'tab1'}>

            <div className="text-center mb-3">
                <h2>Sign in:</h2>
            </div>

            <MDBInput wrapperClass='mb-4' label='Email address' id='loginEmail' type='email' value={loginEmail} onChange={(e) => setLoginEmail(e.target.value)} required/>
            <MDBInput wrapperClass='mb-4' label='Password' id='loginPassword' type='password' value={loginPassword} onChange={(e) => setLoginPassword(e.target.value)} required/>

            <div className="d-flex justify-content-between mx-4 mb-4">
                <MDBCheckbox name='flexCheck' value='' id='flexCheckDefault' label='Remember me' />
                {/* <a href="!#">Forgot password?</a> */}
            </div>

            <MDBBtn className="mb-4 w-100" onClick={submitHandler}>Sign in</MDBBtn>
            {/* <p className="text-center">Not a member? <a href="#!">Register</a></p> */}

            </MDBTabsPane>

            <MDBTabsPane show={justifyActive === 'tab2'}>

            <div className="text-center mb-3">
                <h2>Register:</h2>
            </div>

            <MDBInput wrapperClass='mb-4' label='First Name' id='registerFirstName' type='text' value={regFirstName} onChange={(e) => setRegFirstName(e.target.value)} required/>
            <MDBInput wrapperClass='mb-4' label='Last Name' id='registerLastName' type='text' value={regLastName} onChange={(e) => setRegLastName(e.target.value)} required/>
            <MDBInput wrapperClass='mb-4' label='Email' id='registerEmail' type='email' value={regEmail} onChange={(e) => setRegEmail(e.target.value)} required/>
            <MDBInput wrapperClass='mb-4' label='Password' id='registerPassword' type='password' value={regPassword} onChange={(e) => setRegPassword(e.target.value)} required/>


            <MDBBtn className="mb-4 w-100" onClick={submitHandler}>Sign up</MDBBtn>

            </MDBTabsPane>

        </MDBTabsContent>

        </MDBContainer>
    </div>
  );
}

export default SignIn;
