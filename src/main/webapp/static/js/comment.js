var createComment = function (text, user) {

    var article = document.createElement('article');
    article.className = 'uk-comment uk-margin-top uk-comment-primary';
    var header = document.createElement('header');
    header.className = 'uk-comment-header';
    var img = document.createElement('img');
    img.className = 'uk-comment-avatar';
    img.setAttribute('src', '/images/user/' + user);
    img.setAttribute('width', '40');
    img.setAttribute('height', '30');
    var h4 = document.createElement('h4');
    h4.className = 'uk-comment-title';
    h4.innerText = user;
    var a = document.createElement('a');
    a.href = '/user/' + user;
    a.innerText = ' Profile';
    var divMeta = document.createElement('div');
    divMeta.className = 'uk-comment-meta';
    divMeta.append('Just now |');
    divMeta.appendChild(a);
    header.appendChild(img);
    header.appendChild(h4);
    header.appendChild(divMeta);

    var divText = document.createElement('div');
    divText.className = 'uk-comment-body';
    var p = document.createElement('p');
    p.innerText = text;
    divText.appendChild(p);
    article.appendChild(header);
    article.appendChild(divText);
    return article;
};