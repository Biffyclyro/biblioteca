--
-- PostgreSQL database dump
--

-- Dumped from database version 13.1
-- Dumped by pg_dump version 13.1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'SQL_ASCII';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: biffyclyro
--

CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO biffyclyro;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: livro; Type: TABLE; Schema: public; Owner: biffyclyro
--

CREATE TABLE public.livro (
    id_livro integer NOT NULL,
    ano character varying(255),
    autores character varying(255),
    date date,
    edicao character varying(255),
    editora character varying(255),
    emprestado boolean NOT NULL,
    isbn character varying(255),
    titulo character varying(255),
    id_usuario integer
);


ALTER TABLE public.livro OWNER TO biffyclyro;

--
-- Name: livro_reservas; Type: TABLE; Schema: public; Owner: biffyclyro
--

CREATE TABLE public.livro_reservas (
    livro_id_livro integer NOT NULL,
    reservas_id_usuario integer NOT NULL
);


ALTER TABLE public.livro_reservas OWNER TO biffyclyro;

--
-- Name: usuario; Type: TABLE; Schema: public; Owner: biffyclyro
--

CREATE TABLE public.usuario (
    id_usuario integer NOT NULL,
    limite_livros integer NOT NULL,
    email character varying(255) NOT NULL,
    emprestados integer NOT NULL,
    multa double precision NOT NULL,
    nome character varying(255),
    senha character varying(255) NOT NULL,
    tipo_usuario character varying(255) NOT NULL
);


ALTER TABLE public.usuario OWNER TO biffyclyro;

--
-- Data for Name: livro; Type: TABLE DATA; Schema: public; Owner: biffyclyro
--

COPY public.livro (id_livro, ano, autores, date, edicao, editora, emprestado, isbn, titulo, id_usuario) FROM stdin;
3	2010	Devashish	\N	2013	Inner World	f	97818811717263	Histórias de um Mestre Tântrico	\N
2	2012	Bertrand Russell	\N	2012	Nova Fronteira	f	9788520931516	A conquista da felicidade	\N
4	2008	Baba	\N	4ª	Ananda Marga	f	8586044121	A Liberação da Mente através do Tantra Yoga	\N
5	2020	Harper Lee	\N	36ª	José Olympio	f	9788503009492	O sol é para todos	\N
\.


--
-- Data for Name: livro_reservas; Type: TABLE DATA; Schema: public; Owner: biffyclyro
--

COPY public.livro_reservas (livro_id_livro, reservas_id_usuario) FROM stdin;
\.


--
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: biffyclyro
--

COPY public.usuario (id_usuario, limite_livros, email, emprestados, multa, nome, senha, tipo_usuario) FROM stdin;
1	3	super@admin	0	0	SUPER	$2a$12$haDXV.ItJrAKdagYghZKBO5LSvYFkASrc70LFTuGLvu5z0PwCczCK	SUPER
\.


--
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: biffyclyro
--

SELECT pg_catalog.setval('public.hibernate_sequence', 5, true);


--
-- Name: livro livro_pkey; Type: CONSTRAINT; Schema: public; Owner: biffyclyro
--

ALTER TABLE ONLY public.livro
    ADD CONSTRAINT livro_pkey PRIMARY KEY (id_livro);


--
-- Name: usuario uk_5171l57faosmj8myawaucatdw; Type: CONSTRAINT; Schema: public; Owner: biffyclyro
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT uk_5171l57faosmj8myawaucatdw UNIQUE (email);


--
-- Name: livro_reservas uk_hjvji4icrb05lqeh7t0ab463h; Type: CONSTRAINT; Schema: public; Owner: biffyclyro
--

ALTER TABLE ONLY public.livro_reservas
    ADD CONSTRAINT uk_hjvji4icrb05lqeh7t0ab463h UNIQUE (reservas_id_usuario);


--
-- Name: livro uk_k8si93wtslp275pv65gity1gg; Type: CONSTRAINT; Schema: public; Owner: biffyclyro
--

ALTER TABLE ONLY public.livro
    ADD CONSTRAINT uk_k8si93wtslp275pv65gity1gg UNIQUE (isbn);


--
-- Name: usuario usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: biffyclyro
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id_usuario);


--
-- Name: livro_reservas fk2xttv5gn1m3y1pgu8t52dwikv; Type: FK CONSTRAINT; Schema: public; Owner: biffyclyro
--

ALTER TABLE ONLY public.livro_reservas
    ADD CONSTRAINT fk2xttv5gn1m3y1pgu8t52dwikv FOREIGN KEY (reservas_id_usuario) REFERENCES public.usuario(id_usuario);


--
-- Name: livro fkhej6dc3g148tvhv818ygpe2au; Type: FK CONSTRAINT; Schema: public; Owner: biffyclyro
--

ALTER TABLE ONLY public.livro
    ADD CONSTRAINT fkhej6dc3g148tvhv818ygpe2au FOREIGN KEY (id_usuario) REFERENCES public.usuario(id_usuario);


--
-- Name: livro_reservas fkjhfg4keje1h6x5hvx1q4mmf60; Type: FK CONSTRAINT; Schema: public; Owner: biffyclyro
--

ALTER TABLE ONLY public.livro_reservas
    ADD CONSTRAINT fkjhfg4keje1h6x5hvx1q4mmf60 FOREIGN KEY (livro_id_livro) REFERENCES public.livro(id_livro);


--
-- PostgreSQL database dump complete
--

