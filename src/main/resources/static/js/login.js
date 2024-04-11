const form = document.getElementsByName("main")[0];
const username = document.getElementsByName("username")[0];
const pass = document.getElementsByName("password")[0];
const element = document.querySelector('.login');
const css = `
        font-size: 1vw;
        color: rgb(42, 82, 190);
    `;

function emailTest(input) {
    return !/.*@.*\..*/.test(input.value);
}

function emailValidation() {
    if (emailTest(username)) {
        if(!document.querySelector('.email_error')) {
            username.parentElement.insertAdjacentHTML(
            'beforebegin',
            `<span class="email_error">Please, enter valid email</span>`
        );
        const errorElement = document.querySelector('.email_error');
        errorElement.style.cssText = css;
        element.style.height = "83%";
        }
        return false;
    } else {return true;}
}

function passwordValidation() {
    if (pass.value.trim() === "") {
        if(!document.querySelector('.pass_error')) {
            pass.parentElement.insertAdjacentHTML(
                'beforebegin',
                `<span class="pass_error">Enter your password</span>`
            );
            const errorElement = document.querySelector('.pass_error');
            errorElement.style.cssText = css;
            element.style.height = "83%";
        }
        return false;
    } else {return true;}
}

form.addEventListener("submit", function (event) {
    let isMailValid = emailValidation();
    let isPassValid = passwordValidation();
    if (!(isMailValid && isPassValid)) {
        event.preventDefault()
    } else {
        form.submit();
    }
});
