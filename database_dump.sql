--
-- PostgreSQL database dump
--

-- Dumped from database version 13.8 (Debian 13.8-1.pgdg110+1)
-- Dumped by pg_dump version 14.7 (Ubuntu 14.7-0ubuntu0.22.04.1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

DROP DATABASE IF EXISTS library_manager;
--
-- Name: library_manager; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE library_manager WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'en_US.utf8';


ALTER DATABASE library_manager OWNER TO postgres;

\connect library_manager

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: materials; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.materials (
                                  id bigint NOT NULL,
                                  title character varying NOT NULL,
                                  publisher character varying NOT NULL,
                                  year integer NOT NULL,
                                  quantity integer NOT NULL,
                                  material_type character varying NOT NULL,
                                  book_type character varying,
                                  subject character varying,
                                  genre character varying,
                                  isbn character varying,
                                  volume character varying,
                                  edition character varying
);


ALTER TABLE public.materials OWNER TO postgres;

--
-- Name: materials_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.materials ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.materials_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
    );


--
-- Name: rental_materials; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.rental_materials (
                                         id bigint NOT NULL,
                                         rental_id bigint NOT NULL,
                                         material_id bigint NOT NULL
);


ALTER TABLE public.rental_materials OWNER TO postgres;

--
-- Name: rental_materials_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.rental_materials ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.rental_materials_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
    );


--
-- Name: rentals; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.rentals (
                                id bigint NOT NULL,
                                user_id bigint NOT NULL,
                                return_at date,
                                status character varying
);


ALTER TABLE public.rentals OWNER TO postgres;

--
-- Name: rentals_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.rentals ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.rentals_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
    );


--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
                              id bigint NOT NULL,
                              name character varying NOT NULL,
                              address character varying NOT NULL,
                              email character varying NOT NULL,
                              registration character varying,
                              subjects character varying,
                              type character varying NOT NULL
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.users ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
    );


--
-- Name: materials materials_id_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.materials
    ADD CONSTRAINT materials_id_pkey PRIMARY KEY (id);


--
-- Name: rental_materials rental_materials_id_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rental_materials
    ADD CONSTRAINT rental_materials_id_pkey PRIMARY KEY (id);


--
-- Name: rentals rentals_id_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rentals
    ADD CONSTRAINT rentals_id_pkey PRIMARY KEY (id);


--
-- Name: users users_id_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_id_pkey PRIMARY KEY (id);


--
-- Name: rental_materials material_id_on_rental_materials_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rental_materials
    ADD CONSTRAINT material_id_on_rental_materials_fkey FOREIGN KEY (material_id) REFERENCES public.materials(id);


--
-- Name: rental_materials rental_id_on_rental_materials_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rental_materials
    ADD CONSTRAINT rental_id_on_rental_materials_fkey FOREIGN KEY (rental_id) REFERENCES public.rentals(id);


--
-- Name: rentals user_id_on_rentals_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rentals
    ADD CONSTRAINT user_id_on_rentals_fkey FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- PostgreSQL database dump complete
--

